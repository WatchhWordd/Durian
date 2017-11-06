package com.example.demo.presentation.welcome;

import com.example.demo.BasePresent;
import com.example.demo.BaseView;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/6
 */

public class WelcomeContract {

    interface View extends BaseView<WelcomeContract.Presenter> {
        void showWelcomeSeconds(int seconds);

        void disWelcomePage();

        void jumpMain();

    }

    interface Presenter extends BasePresent {
    }
}
