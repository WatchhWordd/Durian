package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.OkhttpIniter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author zhangyb
 * @description
 * @date 2017/9/19
 */

public class TimeoutIniter implements OkhttpIniter {

    private long timeout = 15;
    private TimeUnit timeoutUnit = TimeUnit.SECONDS;

    public TimeoutIniter(long timeout, TimeUnit timeoutUnit) {
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    public TimeoutIniter() {

    }

    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder builder,
                                           ApiServer apiServer, Context context) {

        if(builder == null){
            builder = new OkHttpClient().newBuilder();
        }
        return builder.connectTimeout(timeout,timeoutUnit)
                .readTimeout(timeout,timeoutUnit)
                .writeTimeout(timeout,timeoutUnit);
    }
}
