package com.example.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.demo.base.utils.CommonUtils;
import com.example.demo.base.utils.CommonUtilsLoader;
import com.github.clientcloud.ApiServer;
import com.github.clientcloud.GitHubCloud;

import java.util.List;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class GitApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (getPackageName().equals(getProcessName(this, android.os.Process.myPid()))) {
            initCommonponent();
        }

    }

    private void initCommonponent() {
        initNetCloud();
    }

    private void initNetCloud() {
        CommonUtils.setCommonLoaderImpl(new CommonUtilsLoader());
        GitHubCloud.getInitializer().setContext(context).setEnablePresetApiServers(true).init();
        ApiServer.setDefaultConfig(ApiServer.Config.CONTENT_TYPE, "application/json; charset=UTF-8");
        ApiServer.setDefaultConfig(ApiServer.Config.ACCESS_TOKEN, "");
        ApiServer.setDefaultConfig(ApiServer.Config.APP_ID, CommonUtils.getAppId(this));
        ApiServer.setDefaultConfig(ApiServer.Config.APP_VERSION, CommonUtils.getVersionName(this));
        ApiServer.setDefaultConfig(ApiServer.Config.APP_KEY, CommonUtils.getAppKey(this));
        ApiServer.setDefaultConfig(ApiServer.Config.LANGUAGE, "zh-cn");
        ApiServer.setDefaultConfig(ApiServer.Config.TIMEZONE, "8");
    }

    public static String getProcessName(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        if (processInfoList == null || processInfoList.isEmpty()) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (info.pid == pid) {
                return info.processName;
            }
        }
        return null;
    }
}
