package com.example.demo.presentation.login;

import android.content.Context;

import com.example.demo.data.net.bean.UserInfo;
import com.example.demo.domain.usecase.GetUserInfo;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    private Context context;
    private GetUserInfo getUserInfo;
    private CompositeDisposable compositeDisposable;

    public LoginPresenter(LoginContract.View loginView, Context context,
                          GetUserInfo getUserInfo) {
        this.loginView = loginView;
        this.context = context;
        this.getUserInfo = getUserInfo;
        this.loginView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {

    }

    @Override
    public void fetchUserInfoByUserName(String userName, String password) {
        String credential = Credentials.basic(userName, password);
        getUserInfo.executeUseCase(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetUserInfo.Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(GetUserInfo.Response response) {

                        UserInfo userInfo = response.getUserInfo();
                        if (userInfo == null) {
                            loginView.onLoginFailed(null);
                        } else {
                            loginView.onLoginSuccess(userInfo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.onLoginFailed(e);
                    }

                    @Override
                    public void onComplete() {
                        loginView.dismissLoginDialog();
                    }
                });
    }

    @Override
    public boolean whetherNeedLog() {
        return false;
    }

    @Override
    public void saveBasicLoginInfo(String loginAccount, String basicCredential) {

    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
