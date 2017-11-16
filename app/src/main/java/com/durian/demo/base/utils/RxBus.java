package com.durian.demo.base.utils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


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

    public  void removeFlowable(Class tClass){
        mBus.hide();
    }
    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext) {
        return toFlowable(eventType).observeOn(scheduler).subscribe(onNext);
    }

    public void unregister(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}