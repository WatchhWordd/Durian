package com.durian.demo.presentation.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.durian.demo.R;

/**
 * @author zhangyb
 * @description
 * @date 2017/12/25
 */

public class WebActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        initWebView();
    }

    private void initWebView() {
    }

    private void initView() {
    }
}
