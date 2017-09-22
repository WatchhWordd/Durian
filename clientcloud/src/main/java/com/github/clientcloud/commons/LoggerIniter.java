package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.OkhttpIniter;

import okhttp3.OkHttpClient;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/22
 */

public class LoggerIniter implements OkhttpIniter{
    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder builder,
                                           ApiServer apiServer, Context context) {

        if (builder ==null){
            builder = new OkHttpClient().newBuilder();
        }
        return builder.addInterceptor(new LoggerInterceptor());
    }
}
