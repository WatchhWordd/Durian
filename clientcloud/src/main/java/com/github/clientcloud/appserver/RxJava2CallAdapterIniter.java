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

public class RxJava2CallAdapterIniter implements OkhttpIniter {
    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder target, ApiServer apiServer, Context context) {
        return null;
    }
}
