package com.durian.demo.base.utils;

import android.content.Context;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/3
 */

public class CommonUtils {

    private static CommonUtils.ICommonLoader loaderImpl;

    public CommonUtils() {
    }

    public static void setCommonLoaderImpl(CommonUtils.ICommonLoader imp) {
        loaderImpl = imp;
    }

    public static String getAppId(Context context) {
        return loaderImpl.getAppId(context);
    }

    public static String getAppKey(Context context) {
        return loaderImpl.getAppKey(context);
    }

    public static String getAppChanel(Context context) {
        return loaderImpl.getAppChanel(context);
    }

    public static String getVersionName(Context context) {
        return loaderImpl.getVersionName(context);
    }


    public static double[] getLocation(Context context, String locationProvider) {
        return loaderImpl.getLocation(context, locationProvider);
    }

    public interface ICommonLoader {
        String getAppId(Context var1);

        String getAppKey(Context var1);

        String getAppChanel(Context var1);

        String getVersionName(Context var1);

        double[] getLocation(Context var1, String var2);
    }
}

