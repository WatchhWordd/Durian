package com.example.githubclientdemo.component;

import com.example.githubclientdemo.login.LoginSupplier;
import com.example.githubclientdemo.modules.AppModule;
import com.example.githubclientdemo.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhangyb on 2016/12/21.
 */

@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface CommonComponent {

    void inject(LoginSupplier loginSupplier);
}
