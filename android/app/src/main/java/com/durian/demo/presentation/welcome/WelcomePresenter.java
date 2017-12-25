package com.durian.demo.presentation.welcome;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/6
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    private static final int COUNT_TIME = 1 * 1000;
    private WelcomeContract.View welcomeView;

    //倒计时
    CountDownTimer countDownTimer = new CountDownTimer(COUNT_TIME, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("Welcome", "disWelcomePage:onTick ");
            int seconds = (int) (millisUntilFinished / 1000);
            welcomeView.showWelcomeSeconds(seconds);
        }

        @Override
        public void onFinish() {
            Log.d("Welcome", "disWelcomePage:onFinish ");
            welcomeView.showWelcomeSeconds(0);
            welcomeView.disWelcomePage();
        }
    };

    public WelcomePresenter(WelcomeContract.View welcomeView, Context context) {
        this.welcomeView = welcomeView;
        this.welcomeView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d("Welcome", "disWelcomePage:start ");
        startCountDownTimer();
    }

    /**
     * 开始倒计时
     */
    private void startCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer.start();
    }
}
