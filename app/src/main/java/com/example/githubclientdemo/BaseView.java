package com.example.githubclientdemo;

import android.content.Context;

/**
 * Created by zhangyb on 2016/12/20.
 */

public interface BaseView<T> {

    int getViewId();

    T getPresenter();

    void setPresenter(T presenter);

    Context getCxt();

    MyApplication getAppContext();
}
