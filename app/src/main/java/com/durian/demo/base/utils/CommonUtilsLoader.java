package com.durian.demo.base.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/3
 */

public class CommonUtilsLoader implements CommonUtils.ICommonLoader {
    public static final String KEY_APP_ID = "APP_ID";
    public static final String KEY_APP_KEY = "APP_KEY";
    public static final String KEY_APP_CHANNEL = "U_ANALYTICS_CHANNEL";

    @Override
    public String getAppId(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String appId = appInfo.metaData.getString(KEY_APP_ID);
            return appId;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getAppKey(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String appKey = appInfo.metaData.getString(KEY_APP_KEY);
            return appKey;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getAppChanel(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String appChannel = appInfo.metaData.getString(KEY_APP_CHANNEL);
            if (appChannel == null) {
                appChannel = String.valueOf(appInfo.metaData.getInt(KEY_APP_CHANNEL));
            }
            return appChannel;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取android:versionName
     */
    @Override
    public String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo packageInfo;
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 获取地理位置坐标
     *
     * @param locationProvider 取值LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
     */
    @Override
    public double[] getLocation(Context context, String locationProvider) {
        double[] latlng = {0.0, 0.0};
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        try {
            if (locationManager.isProviderEnabled(locationProvider)) {
                if (ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Location location = locationManager.getLastKnownLocation(locationProvider);
                    if (location != null) {
                        latlng[0] = location.getLatitude();
                        latlng[1] = location.getLongitude();
                    }
                }

            } else {
                LocationListener locationListener = new LocationListener() {

                    // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    // Provider被enable时触发此函数，比如GPS被打开
                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    // Provider被disable时触发此函数，比如GPS被关闭
                    @Override
                    public void onProviderDisabled(String provider) {

                    }

                    // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                    @Override
                    public void onLocationChanged(Location location) {
                    }
                };
                if (ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0,
                            locationListener);
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latlng[0] = location.getLatitude(); // 经度
                        latlng[1] = location.getLongitude(); // 纬度
                    }
                    locationManager.removeUpdates(locationListener);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlng;
    }
}
