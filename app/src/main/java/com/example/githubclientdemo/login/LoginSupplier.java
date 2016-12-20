package com.example.githubclientdemo.login;

import android.support.annotation.NonNull;

import com.example.githubclientdemo.BasePresenter;
import com.example.githubclientdemo.BaseSupplier;
import com.example.githubclientdemo.entities.UserInfo;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class LoginSupplier extends BaseSupplier<UserInfo> implements LoginContract.Persistence{

    public LoginSupplier(BasePresenter presenter, MutableRepository supplier) {
        super(presenter, supplier);
    }

    @Override
    public Result<UserInfo> loadData() {
        return null;
    }

    @NonNull
    @Override
    public Result<UserInfo> get() {
        return null;
    }

    @Override
    public void saveUserInfo(String loginAccess, String basicCredential) {

    }

    @Override
    public boolean isNeedLogin() {
        return false;
    }

    @Override
    public void saveData(Object data) {

    }

    @Override
    public void deleteData(Object data) {

    }

    @Override
    public Object retrieveData() {
        return null;
    }
}
