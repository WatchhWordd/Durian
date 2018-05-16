package com.durian.demo.base.listener;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/27
 */
public interface ScrollViewListener<T> {

    void scrollViewChange(T scrollView, int x, int y);

}
