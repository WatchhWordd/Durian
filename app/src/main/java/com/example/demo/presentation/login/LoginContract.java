package com.example.demo.presentation.login;

import com.example.demo.BasePresent;
import com.example.demo.BaseView;
import com.example.demo.data.net.bean.UserInfo;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public class LoginContract {

    interface View extends BaseView<Presenter> {
        void initViews();

        void showLoginDialog();

        void dismissLoginDialog();

        void doLogin();

        void onLoginSuccess(UserInfo userInfo);

        void onLoginFailed(Throwable error);

        void jumpToMain();
    }

    interface Presenter extends BasePresent {

        void fetchUserInfoByUserName(String userName, String password);

        boolean whetherNeedLog();

        void saveBasicLoginInfo(String loginAccount, String basicCredential);

        void cancelRequest();
    }
}
