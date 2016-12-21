package com.example.githubclientdemo.modules;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangyb on 2016/12/21.
 */

@Module
public class NetModule {

    private Context mContext;
    private String  mBaseUrl;

    public NetModule(Context mContext, String mBaseUrl) {
        this.mContext = mContext;
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    ExecutorService providersExecutorService(){
        return Executors.newCachedThreadPool();
    }
}
