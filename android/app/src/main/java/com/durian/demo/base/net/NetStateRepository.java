package com.durian.demo.base.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.lang.ref.WeakReference;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class NetStateRepository implements NetDataSource {

    private static final String INTENT_ACTION_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private static volatile NetStateRepository instance;
    private WeakReference<Context> weakReference;

    public NetStateRepository(Context context) {
        weakReference = new WeakReference<>(context.getApplicationContext());
    }

    public static NetStateRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (NetStateRepository.class) {
                if (instance == null) {
                    instance = new NetStateRepository(context);
                }
            }
        }
        return instance;
    }

    @Override
    public Context getContext() {
        return weakReference.get();
    }

    @Override
    public NetState getNetInfo() {
        NetState netInfo = new NetState();
        NetType netType = getNetType();
        netInfo.setNetType(netType);
        netInfo.setIp(getIp());
        netInfo.setSsid(getSsid());
        netInfo.setGetWay(getGateway());
        return netInfo;
    }

    @Override
    public String getIp() {
        try {
            return tryGetIp();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isConnected() {
        return getNetInfo().isConnection();
    }

    @Override
    public Single<Boolean> isConnectedAsSingle() {
        return Single.just(isConnected());
    }

    @Override
    public Observable<NetState> getNetStateObserver() {

        return Observable.create(observer -> {
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    observer.onNext(getNetInfo());
                }
            };

            Disposable disposable = new Disposable() {
                @Override
                public void dispose() {
                    getContext().unregisterReceiver(broadcastReceiver);
                }

                @Override
                public boolean isDisposed() {
                    return true;
                }
            };

            observer.setDisposable(disposable);
            IntentFilter intentFilter = new IntentFilter(INTENT_ACTION_NETWORK_CHANGE);
            getContext().registerReceiver(broadcastReceiver, intentFilter);
        });
    }

    @Override
    public Single<NetState> getNetStateSingle() {
        return Single.just(getNetInfo());
    }

    private static Pair<NetType, Integer> pair(NetType netType, Integer networkType) {
        return Pair.create(netType, networkType);
    }

    public NetType getNetType() {
        ConnectivityManager connManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        List<NetworkInfo.State> positiveStateList = Arrays.asList(
                NetworkInfo.State.CONNECTED, NetworkInfo.State.CONNECTING);
        return Observable.just(pair(NetType.ETHERNET, ConnectivityManager.TYPE_ETHERNET),
                pair(NetType.WIFI, ConnectivityManager.TYPE_WIFI),
                pair(NetType.MOBILE, ConnectivityManager.TYPE_MOBILE))
                .filter(new Predicate<Pair<NetType, Integer>>() {
                    @Override
                    public boolean test(Pair<NetType, Integer> pair) throws Exception {
                        NetworkInfo networkInfo = connManager.getNetworkInfo(pair.second);
                        return networkInfo != null && positiveStateList.contains(networkInfo.getState());
                    }
                })
                .first(pair(NetType.NONE, Integer.MAX_VALUE))
                .map(pair -> pair.first).blockingGet();
    }

    private String getSsid() {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getSSID();
    }

    private String getGateway() {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        long data = dhcpInfo.gateway;
        return String.format("%s.%s.%s.%s",
                data & 0xff, (data >> 8) & 0xff, (data >> 16) & 0xff, (data >> 24) & 0xff);
    }

    @Nullable
    private String tryGetIp() throws SocketException {
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
             networkInterfaces.hasMoreElements(); ) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            for (Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                 addressEnumeration.hasMoreElements(); ) {
                InetAddress address = addressEnumeration.nextElement();
                if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                    return address.getHostAddress();
                }
            }
        }
        return null;
    }
}
