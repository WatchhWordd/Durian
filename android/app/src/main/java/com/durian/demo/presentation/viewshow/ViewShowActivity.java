package com.durian.demo.presentation.viewshow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.durian.demo.R;
import com.durian.demo.base.widget.ShaderView;

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
        FrameLayout rootParent = findViewById(R.id.root_parent);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ShaderView areaView = new ShaderView(this);
        areaView.setLayoutParams(layoutParams);
        rootParent.addView(areaView,layoutParams);
    }

    @Override
    public void setPresenter(ViewShowContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
