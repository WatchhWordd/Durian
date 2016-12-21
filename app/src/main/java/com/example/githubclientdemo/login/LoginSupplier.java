package com.example.githubclientdemo.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.githubclientdemo.BasePresenter;
import com.example.githubclientdemo.BaseSupplier;
import com.example.githubclientdemo.entities.UserInfo;
import com.example.githubclientdemo.params.UserLoginParams;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class LoginSupplier extends BaseSupplier<UserInfo> implements LoginContract.Persistence {

    private Context mContext;
    private MutableRepository<UserLoginParams> mSupplier;
    @Inject
    Retrofit retrofit;


    public LoginSupplier(BasePresenter presenter, MutableRepository<UserLoginParams> supplier) {
        super();
        mContext = presenter.getView().getCxt();
        mSupplier = supplier;
        mPresenter = presenter;
        mPresenter.getView().getAppContext().getNetComponent().inject(this);
    }

    @Override
    public Result<UserInfo> loadData() {
        UserLoginParams userLoginParams = mSupplier.get();
        if (userLoginParams == null) {
            Result.failure();
        } else if (TextUtils.isEmpty(userLoginParams.userName)
                || TextUtils.isEmpty(userLoginParams.userPassword)) {
            Result.failure(new Throwable("用户名或密码为空"));
        }
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
