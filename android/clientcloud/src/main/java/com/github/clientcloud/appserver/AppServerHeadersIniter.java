package com.github.clientcloud.appserver;

import android.content.Context;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.OkhttpIniter;

import okhttp3.OkHttpClient;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/19
 */

public class AppServerHeadersIniter implements OkhttpIniter {
    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder target,
                                           ApiServer apiServer, Context context) {
        if (target ==null){
            target = new OkHttpClient().newBuilder();
        }
        return target.addInterceptor(new AppServerHeadersInterceptor(apiServer));
    }
}
