package com.durian.demo.presentation.view.dialview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.durian.demo.R;
import com.durian.demo.base.widget.ZhiFuBaoCircle;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/19
 */
public class ViewShowActivity extends AppCompatActivity implements ViewShowContract.View{

    private ViewShowContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_view);
        initView();
    }

    private void initView() {
        ZhiFuBaoCircle zhiFuBaoCircle = findViewById(R.id.zhiFuView);
        zhiFuBaoCircle.setSesameValues(600);
    }

    @Override
    public void setPresenter(ViewShowContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
