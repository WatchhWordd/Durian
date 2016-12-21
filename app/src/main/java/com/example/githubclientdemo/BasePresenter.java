package com.example.githubclientdemo;

/**
 * Created by zhangyb on 2016/12/20.
 */

public interface BasePresenter {

    void start();

    void resume();

    void pause();

    BaseView getView();
    BaseSupplier getSupplier();

    void end();

}
