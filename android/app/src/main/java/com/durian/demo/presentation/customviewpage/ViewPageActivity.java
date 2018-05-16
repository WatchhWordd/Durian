package com.durian.demo.presentation.customviewpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class ViewPageActivity extends AppCompatActivity implements ViewPagerContract.View{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(ViewPagerContract.Presenter presenter) {

    }
}
