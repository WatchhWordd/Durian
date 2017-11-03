package com.example.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

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
        GitHubCloud.getInitializer().setContext(context).setEnablePresetApiServers(true).init();
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
