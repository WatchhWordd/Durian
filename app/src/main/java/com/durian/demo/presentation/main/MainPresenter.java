package com.durian.demo.presentation.main;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        this.mainView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
