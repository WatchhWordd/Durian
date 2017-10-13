package com.example.githubclientdemo;

import android.app.Application;

import com.example.githubclientdemo.component.CommonComponent;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class MyApplication extends Application {

    private CommonComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDragCommonponent();
    }

    private void initDragCommonponent() {
        initNetCloud();
    }

    private void initNetCloud() {
    }
}
