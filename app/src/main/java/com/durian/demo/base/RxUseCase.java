package com.durian.demo.base;


import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description UseCase基类
 * @date 2017/9/22
 */

public abstract class RxUseCase<Q, P> {

    protected abstract Observable<P> buildUseCaseObservable(Q requestValues);

    public Observable<P> executeUseCase() {
        return executeUseCase(null);
    }

    public Observable<P> executeUseCase(Q requestValues) {
        return buildUseCaseObservable(requestValues);
    }

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }

}
