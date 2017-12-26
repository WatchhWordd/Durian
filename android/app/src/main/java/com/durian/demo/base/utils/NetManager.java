package com.durian.demo.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 网络状态管理类
 *
 * @author john
 */
public class NetManager {
    private static final String TAG = "NetManager";
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;
    public static final int NETWORK_ETHERNET = 3;

    private Context mContext;

    private static volatile NetManager nm;

    private List<OnNetChangedReceiver> receiverList;
    private OnNetChangedReceiver receiver;

    public List<OnNetChangedReceiver> getOnNetChangedReceiverList() {
        return receiverList;
    }

    public void addOnNetChangedReceiver(OnNetChangedReceiver receiver) {
        receiverList.add(receiver);
    }

    public void removeOnNetChangedReceiver(OnNetChangedReceiver receiver) {
        if (receiverList.contains(receiver)) {
            receiverList.remove(receiver);
        }
    }

    private NetManager() {
        receiverList = new ArrayList<OnNetChangedReceiver>();
    }

    public static NetManager getInstance(Context mContext) {
        if (nm == null) {
            synchronized (NetManager.class) {
                if (nm == null) {
                    nm = new NetManager();
                }
            }
        }
        nm.mContext = mContext;
        return nm;
    }

    /**
     * 获取网络状态
     *
     * @return 网络状态
     */
    public int getNetworkState() {
        ConnectivityManager connManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info;
        State state;
        // Ethernet
        info = connManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info != null) {
            state = info.getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORK_ETHERNET;
            }
        }
        // Wifi
        info = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            state = info.getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORK_WIFI;
            }
        }
        // 3G
        info = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info != null) {
            state = info.getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORK_MOBILE;
            }
        }
        return NETWORK_NONE;
    }


    /**
     * 获取本机IP
     *
     * @return ip
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public interface OnNetChangedReceiver {
        void onNetChange();
    }
}
