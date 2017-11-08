package com.durian.demo.presentation.login;

import android.content.Context;
import android.text.TextUtils;

import com.baoyz.treasure.Treasure;
import com.durian.demo.base.utils.ACache;
import com.durian.demo.base.utils.ConfigUtil;
import com.durian.demo.base.utils.PreferenceService;
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.domain.usecase.GetUserInfo;

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

    private PreferenceService preferenceService;
    private ACache aCache;

    public LoginPresenter(LoginContract.View loginView, Context context,
                          GetUserInfo getUserInfo) {
        this.loginView = loginView;
        this.context = context;
        this.getUserInfo = getUserInfo;
        this.preferenceService = Treasure.get(this.context, PreferenceService.class);
        this.aCache = ACache.get(this.context);
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
                            loginView.onLoginFailed("登录失败");
                        } else {
                            saveBasicLoginInfo(userName, credential);
                            loginView.onLoginSuccess(userInfo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.onLoginFailed("登录失败");
                    }

                    @Override
                    public void onComplete() {
                        loginView.dismissLoginDialog();
                    }
                });
    }

    @Override
    public boolean whetherNeedLog() {
        return TextUtils.isEmpty(preferenceService.getBasicCredential());
    }

    @Override
    public void saveBasicLoginInfo(String loginAccount, String basicCredential) {
        preferenceService.setUsername(loginAccount);
        preferenceService.setLoginAccount(loginAccount);
        if (whetherNeedLog()) {//认证需要重置
            preferenceService.setBasicCredential(basicCredential);
        }
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void saveData(UserInfo data) {
      aCache.put(ConfigUtil.S_USER_INFO,data);
    }
}
