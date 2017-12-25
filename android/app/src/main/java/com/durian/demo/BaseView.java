package com.durian.demo;

/**
 * @author zhangyb
 * @description
 * @date 2017/10/31
 */

public interface BaseView<T extends BasePresent> {

    void setPresenter(T presenter);
}
