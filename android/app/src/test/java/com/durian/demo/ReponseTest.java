package com.durian.demo;

import android.util.Log;

import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.domain.usecase.GetUserInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

/**
 * @author zhangyb
 * @description
 * @date 2018/3/28
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class ,sdk = 23)
public class ReponseTest {
    
    @Before
    public void setUp(){
        ShadowLog.stream = System.out;
        initRxJava();
    }

    private void initRxJava() {
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Test
    public void testUserInfo(){
        String credential = Credentials.basic(null, null);
        GitDataInjection.provideGetUserInfo().executeUseCase(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetUserInfo.Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GetUserInfo.Response response) {

                        UserInfo userInfo = response.getUserInfo();
                        Assert.assertEquals("zhangyabin", userInfo.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Test", e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
