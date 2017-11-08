package com.durian.gitlogger;

import android.text.TextUtils;
import android.util.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;


/**
 * @author zhangyb
 * @description logger
 * @date 2017/11/8
 */

public class DurianLogger {

    private static String TAG = DurianLogger.class.getSimpleName();
    private static final String ANDROID_HANDLER = "com.android.internal.logging.AndroidHandler";
    private static AtomicBoolean isAndroidLoggingHandlerReplaced = new AtomicBoolean(false);
    private static List<String> loggerNameList = new ArrayList<>();

    private Logger logger;
    private String loggerName;
    private boolean isDebugApp = true;
    private boolean isSaveToFile = false;
    private String saveFolder;
    private String fileName;
    private int maxFileSize = 2 * 1024 * 1024;
    private int fileCount = 10;
    private boolean isAppendFile = true;

    public DurianLogger setLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public DurianLogger setDebugApp(boolean debugApp) {
        isDebugApp = debugApp;
        return this;
    }

    public DurianLogger setIsSaveToFile(boolean saveToFile) {
        isSaveToFile = saveToFile;
        return this;
    }

    public DurianLogger setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
        return this;
    }

    public DurianLogger setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public DurianLogger setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }

    public DurianLogger setFileCount(int fileCount) {
        this.fileCount = fileCount;
        return this;
    }

    public DurianLogger setIsAppend(boolean append) {
        isAppendFile = append;
        return this;
    }

    public Logger build() {
        TAG += "_[" + loggerName + "]";

        createLogger();
        initLogLevel();
        initLogFile();
        tryFixAndroidLoggingHandler();

        logger.info("Hello world ! I'm {} :)", loggerName);
        return logger;
    }

    // ------------------------------------------------------------------------

    private void createLogger() {
        Log.d(TAG, "logger name: " + loggerName);

        if (TextUtils.isEmpty(loggerName)) {
            throw new IllegalArgumentException("ERROR! Illegal logger name: " + loggerName);
        }

        if (loggerNameList.contains(loggerName)) {
            throw new IllegalArgumentException("ERROR! This logger has already existed: " + loggerName);
        }

        logger = LoggerFactory.getLogger(loggerName);
        loggerNameList.add(loggerName);
    }

    private void initLogLevel() {
        Level level = Level.INFO;
        if (isDebugApp) {
            level = Level.FINE;
        }

        getJavaLogger().setLevel(level);

        Log.d(TAG, "isDebugApp: " + isDebugApp);
        Log.d(TAG, "logger level: " + level);
    }

    private java.util.logging.Logger getJavaLogger() {
        return java.util.logging.Logger.getLogger(loggerName);
    }

    private void initLogFile() {
        if (!isSaveToFile) {
            return;
        }

        if (TextUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("ERROR! Illegal log file name: " + fileName);
        }

        if (TextUtils.isEmpty(saveFolder)) {
            throw new IllegalArgumentException("ERROR! Illegal log file sava folder: " + saveFolder);
        }

        if (!createSaveFolder(saveFolder)) {
            Log.e(TAG, "ERROR! Can not create log file folder: " + saveFolder +
                    ", so no more log files!");
            return;
        }

        String pattern = saveFolder + File.separator + fileName;
        Log.d(TAG, "log file name pattern: " + pattern);

        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(pattern, maxFileSize, fileCount, isAppendFile);
        } catch (IOException e) {
            Log.e(TAG, "addFileHandler: Failed to create fileHandler !");
            e.printStackTrace();
            return;
        }

        fileHandler.setFormatter(new Formatter());
        getJavaLogger().addHandler(fileHandler);
    }

    private boolean createSaveFolder(String saveFolder) {
        File file = new File(saveFolder);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return file.isDirectory();
    }

    private void tryFixAndroidLoggingHandler() {
        if (!isAndroidLoggingHandlerReplaced.compareAndSet(false, true)) {
            Log.w(TAG, "android logging handler has been replaced !");
            return;
        }
        fixAndroidLoggingHandler();
    }

    // ------------------------------------------------------------------------

    public static void fixAndroidLoggingHandler() {
        String tag = DurianLogger.class.getSimpleName();
        java.util.logging.Logger rootJavaLogger = LogManager.getLogManager().getLogger("");
        Handler androidHandler = null;
        for (Handler handler : rootJavaLogger.getHandlers()) {
            if (ANDROID_HANDLER.equals(handler.getClass().getName())) {
                androidHandler = handler;
                break;
            }
        }
        if (androidHandler == null) {
            Log.w(tag, "android logging handler has been replaced !");
        }
        rootJavaLogger.removeHandler(androidHandler);
        UpLoggingHandler newHandler = new UpLoggingHandler();
        rootJavaLogger.addHandler(newHandler);
        Log.d(tag, "replace handler: " + androidHandler + " -> " + newHandler);
    }

    private static class UpLoggingHandler extends Handler {
        private static final String TAG = UpLoggingHandler.class.getSimpleName();
        private static final int TAG_MAX_LENGTH = 30;

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void publish(LogRecord record) {
            if (!super.isLoggable(record)) {
                return;
            }

            String tag;
            String name = record.getLoggerName();
            if (name.length() > TAG_MAX_LENGTH) {
                tag = name.substring(name.length() - TAG_MAX_LENGTH);
            } else {
                tag = name;
            }

            int level;
            int value = record.getLevel().intValue();
            if (value >= 1000) {
                level = Log.ERROR;
            } else if (value >= 900) {
                level = Log.WARN;
            } else if (value >= 800) {
                level = Log.INFO;
            } else {
                level = Log.DEBUG;
            }

            try {
                Log.println(level, tag, formatMessage(record));
                if (record.getThrown() != null) {
                    Log.println(level, tag, Log.getStackTraceString(record.getThrown()));
                }
            } catch (RuntimeException e) {
                Log.e(TAG, "Error logging message.", e);
            }
        }
    }

    // ------------------------------------------------------------------------

    private static String formatMessage(LogRecord record) {
        String className = record.getSourceClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        return "[" + className + "#" + record.getSourceMethodName() + "] " + record.getMessage();
    }

    private static class Formatter extends java.util.logging.Formatter {
        private Date date = new Date();

        @Override
        public String format(LogRecord record) {
            date.setTime(record.getMillis());
            return date + " " + record.getLevel() + ": " + DurianLogger.formatMessage(record) + "\n";
        }
    }

}
