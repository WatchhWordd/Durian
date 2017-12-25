package com.durian.demo.presentation.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.baoyz.treasure.Treasure;
import com.durian.demo.R;
import com.durian.demo.base.utils.ACache;
import com.durian.demo.base.utils.ConfigUtil;
import com.durian.demo.base.utils.PreferenceService;
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.presentation.login.LoginActivity;
import com.durian.demo.presentation.main.MainActivity;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/6
 */

public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View {

    private WelcomeContract.Presenter presenter;
    private TextView progressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        new WelcomePresenter(this, this);
        this.presenter.start();
    }

    private void initView() {
        progressView = (TextView) findViewById(R.id.id_welcome_seconds);
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showWelcomeSeconds(int seconds) {
        progressView.setText(seconds + "s");
    }

    @Override
    public void disWelcomePage() {
        Log.d("Welcome", "disWelcomePage: ");
        Intent intent = new Intent();
        PreferenceService sharedPreferences = Treasure.get(this, PreferenceService.class);
        UserInfo userInfo = (UserInfo) ACache.get(this.getApplicationContext())
                .getAsObject(ConfigUtil.S_USER_INFO);
        if (sharedPreferences != null && sharedPreferences.getUsername()
                .equalsIgnoreCase("WatchhWordd") && userInfo != null) {
            intent.setClass(this, MainActivity.class);
            intent.putExtra("userInfo", userInfo);
        } else {
            intent.setClass(this, LoginActivity.class);
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    public void jumpMain() {

    }
}
