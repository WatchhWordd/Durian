package com.example.demo;

import android.app.Application;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class MyApplication extends Application {


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
