package com.durian.gitlogger;

import android.content.Context;


import org.slf4j.Logger;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class Log {
    private static Logger logger;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);

    public static  Logger getLogger(){
        return logger;
    }
    public static void initialize(Context context) {
        if(!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("ERROR! Already initialized !");
        } else {
            DurianLogger builder = new DurianLogger();
            builder.setLoggerName("[Durian]");
            File externalCacheDir = context.getExternalCacheDir();
            if(externalCacheDir != null) {
                builder.setIsSaveToFile(true).setFileName("durian.log").setSaveFolder(externalCacheDir
                        .getAbsolutePath()).setMaxFileSize(2097152).setFileCount(10);
            } else {
                android.util.Log.e("durian.base.Log", "context.getExternalCacheDir() returns null," +
                        " so no log will save to file");
            }
            logger = builder.build();
        }
    }
}
