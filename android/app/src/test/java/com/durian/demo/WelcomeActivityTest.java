package com.durian.demo;

import android.content.Intent;

import com.durian.demo.presentation.login.LoginActivity;
import com.durian.demo.presentation.main.MainActivity;
import com.durian.demo.presentation.welcome.WelcomeActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowLog;

/**
 * @author zhangyb
 * @description robolectric test
 * @date 2018/3/27
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 23)
public class WelcomeActivityTest {

    private WelcomeActivity welcomeActivity;
    @Before
    public void setUp(){
        ShadowLog.stream = System.out;
        welcomeActivity = Robolectric.setupActivity(WelcomeActivity.class);
        welcomeActivity.getApplication().onCreate();
    }

    @Test
    public void testWelcomeActivity(){
        Assert.assertNotNull(welcomeActivity);
    }

    @Test
    public void testJumpLogin(){
        ShadowActivity shadowActivity = Shadows.shadowOf(welcomeActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        // 校验Intent的正确性
        Assert.assertEquals(nextIntent.getComponent().getClassName(), LoginActivity.class.getName());
    }
}
