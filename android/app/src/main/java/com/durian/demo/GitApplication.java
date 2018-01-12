package com.durian.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.baoyz.treasure.Treasure;
import com.durian.demo.base.utils.CommonUtils;
import com.durian.demo.base.utils.CommonUtilsLoader;
import com.durian.demo.base.utils.PreferenceService;
import com.durian.gitlogger.CrashHandler;
import com.durian.gitlogger.Log;
import com.github.clientcloud.ApiServer;
import com.github.clientcloud.GitHubCloud;
import com.taobao.sophix.SophixManager;

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
            initQueryLoad();
        }

    }

    private void initQueryLoad() {
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void initCommonponent() {
        initLogger();
        initNetCloud();
        initAppModule();
    }

    private void initLogger() {
        Log.initialize(this);
        CrashHandler.getInstance().initialize(this);
        CrashHandler.getInstance().setHandlerHook((var1, var2) -> {
            Log.getLogger().error("app exit because exception");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        });

    }

    private void initAppModule() {
        initPreference();
    }

    private void initPreference() {
        Treasure.get(this, PreferenceService.class);
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
