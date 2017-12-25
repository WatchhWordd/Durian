package com.durian.gitlogger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @author zhangyb
 * @date 2017/11/8
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;
    private CrashHandler.UncaughtException hook;
    private boolean isInitialize = false;

    private static class SingletonHodler {
        private static CrashHandler instance = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return SingletonHodler.instance;
    }
    public CrashHandler initialize(Context context) {
        this.context = context;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        isInitialize = true;
        return this;
    }

    public void setHandlerHook(CrashHandler.UncaughtException hook) {
        this.checkInitialized();
        this.hook = hook;
    }

    private void checkInitialized() {
        if (!this.isInitialize) {
            throw new RuntimeException("ERROR ! CrashHandler Not Initialized ! Call CrashHandler.initialize(Context context)");
        }
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            this.handleUncaughtException(thread, throwable);
        } catch (Exception var4) {
            Log.getLogger().error("An error occurred while handling exception", var4);
        }

    }

    private void handleUncaughtException(Thread thread, Throwable throwable) {
        this.logTitle();
        this.logAppInfo();
        this.logDeviceInfo();
        this.logExceptionInfo(thread, throwable);
        if (null != this.defaultHandler) {
            this.defaultHandler.uncaughtException(thread, throwable);
        }

        if (this.hook != null) {
            this.hook.uncaughtException(thread, throwable);
        }

    }

    private void logTitle() {
        Log.getLogger().error(" __   __        __            /  /  /");
        Log.getLogger().error("/  ` |__)  /\\  /__` |__|     /  /  / ");
        Log.getLogger().error("\\__, |  \\ /~~\\ .__/ |  |    .  .  . ");
    }

    private void logAppInfo() {
        try {
            @SuppressLint("WrongConstant")
            PackageInfo packageInfo = this.context.getPackageManager()
                    .getPackageInfo(this.context.getPackageName(), 1);
            if (null != packageInfo) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = String.valueOf(packageInfo.versionCode);
                Log.getLogger().error("versionName: " + versionName);
                Log.getLogger().error("versionCode: " + versionCode);
            }
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
            Log.getLogger().error("An error occurred while collecting application info", var4);
        }

    }

    private void logDeviceInfo() {
        Field[] fields = Build.class.getDeclaredFields();
        Field[] var2 = fields;
        int var3 = fields.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];

            try {
                field.setAccessible(true);
                Log.getLogger().error(String.format(Locale.US, "DeviceInfo[%s] : %s", new Object[]{field.getName(), field.get((Object) null)}));
            } catch (Exception var7) {
                Log.getLogger().error("An error occurred while collecting Device info", var7);
            }
        }

    }

    private void logExceptionInfo(Thread thread, Throwable throwable) {
        Log.getLogger().error(String.format(Locale.US, "UncaughtException, THREAD: %s NAME: %s ID: %d", new Object[]{thread, thread.getName(), Long.valueOf(thread.getId())}));
        this.logExceptionStackTrace(throwable);
        this.logExceptionCause(throwable);
    }

    private void logExceptionStackTrace(Throwable throwable) {
        StackTraceElement[] trace = throwable.getStackTrace();
        Log.getLogger().error("**** STACKTRACE BEGIN ****");
        Log.getLogger().error("exception = " + throwable);
        StackTraceElement[] var3 = trace;
        int var4 = trace.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            StackTraceElement traceItem = var3[var5];
            Log.getLogger().error(String.format(Locale.US, "at %s.%s(%s:%d)", new Object[]{traceItem.getClassName(), traceItem.getMethodName(), traceItem.getFileName(), Integer.valueOf(traceItem.getLineNumber())}));
        }

        Log.getLogger().error("**** STACKTRACE END **** ");
    }

    private void logExceptionCause(Throwable throwable) {
        if (throwable.getCause() != null) {
            Log.getLogger().error("**** CAUSE BEGIN ****");
            Log.getLogger().error("getCause() = " + throwable.getCause());
            StackTraceElement[] causeTrace = throwable.getCause().getStackTrace();
            if (causeTrace != null) {
                StackTraceElement[] var3 = causeTrace;
                int var4 = causeTrace.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    StackTraceElement causeTraceItem = var3[var5];
                    Log.getLogger().error("at " + causeTraceItem);
                }
            }

            Log.getLogger().error("**** CAUSE END ****");
        }
    }

    public interface UncaughtException {
        void uncaughtException(Thread var1, Throwable var2);
    }
}
