package com.example.githubclientdemo;

import android.app.Application;

import com.example.githubclientdemo.component.CommonComponent;
import com.example.githubclientdemo.component.DaggerCommonComponent;
import com.example.githubclientdemo.modules.AppModule;
import com.example.githubclientdemo.modules.NetModule;

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
        buildCommonComponent();
    }

    private void buildCommonComponent() {

        NetModule netModule = new NetModule(this, "https://api.github.com");
        AppModule appModule = new AppModule(this);
        netComponent= DaggerCommonComponent.builder().netModule(netModule)
                .appModule(appModule)
                .build();


    }

    public CommonComponent getNetComponent() {
        return netComponent;
    }

    public void setNetComponent(CommonComponent netComponent) {
        this.netComponent = netComponent;
    }
}
