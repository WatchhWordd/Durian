package com.durian.demo.presentation.view.customviewpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.durian.demo.R;
import com.durian.demo.presentation.view.customviewpage.view.CustomViewPager;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class ViewPageActivity extends AppCompatActivity implements ViewPagerContract.View {

    private ViewPagerContract.Presenter presenter;
    private CustomViewPager customViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        initView();
    }

    private void initView() {
        initViewPager();
    }

    private void initViewPager() {
        customViewPager = findViewById(R.id.id_custom_view);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        customViewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void setPresenter(ViewPagerContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
