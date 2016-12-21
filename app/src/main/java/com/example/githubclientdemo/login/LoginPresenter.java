package com.example.githubclientdemo.login;

import android.support.annotation.NonNull;

import com.example.githubclientdemo.BaseSupplier;
import com.example.githubclientdemo.BaseView;
import com.example.githubclientdemo.entities.UserInfo;
import com.google.android.agera.Receiver;
import com.google.android.agera.Updatable;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class LoginPresenter implements LoginContract.Presenter,Updatable,Receiver<UserInfo> {
    @Override
    public void featchUserInfoByUserName(String name, String password) {

    }

    @Override
    public LoginContract.Persistence getPersistence() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public BaseView getView() {
        return null;
    }

    @Override
    public BaseSupplier getSupplier() {
        return null;
    }

    @Override
    public void end() {

    }

    @Override
    public void update() {

    }

    @Override
    public void accept(@NonNull UserInfo value) {

    }
}
