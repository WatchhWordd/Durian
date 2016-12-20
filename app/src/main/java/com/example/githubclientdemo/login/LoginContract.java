package com.example.githubclientdemo.login;

import com.example.githubclientdemo.BasePersistence;
import com.example.githubclientdemo.BasePresenter;
import com.example.githubclientdemo.BaseView;
import com.example.githubclientdemo.entities.UserInfo;

/**
 * Created by zhangyb on 2016/12/20.
 */

public class LoginContract {

    public interface View extends BaseView {

        void initView();

        void showLoginDialog();

        void dismissLoginDialog();

        void doLogin();

        void onUserInfoUpdate(UserInfo userInfo);

        void onUserInfoFailed(Throwable error);

        void jumpToMain();
    }

    public interface Presenter extends BasePresenter{

        void featchUserInfoByUserName(String name,String password);
        LoginContract.Persistence getPersistence();

    }

    public interface Persistence extends BasePersistence{

        void saveUserInfo(String loginAccess,String basicCredential);
        boolean isNeedLogin();
    }
}
