package com.durian.demo.base.utils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class RxBus {
    //相当于Rxjava1.x中的Subject
    private final Relay<Object> mBus;

    private RxBus() {
        //调用toSerialized()方法，保证线程安全
        mBus = PublishRelay.create().toSerialized();
    }

    private static class Holder {
        private static RxBus instance = new RxBus();
    }

    public static RxBus getInstance() {
        return Holder.instance;
    }

    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o) {
        mBus.accept(o);
    }

    /**
     * 确定接收消息的类型
     *
     * @param <T>
     * @param aClass
     * @return
     */
    public <T> Observable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }


    public void removeRxBus(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}