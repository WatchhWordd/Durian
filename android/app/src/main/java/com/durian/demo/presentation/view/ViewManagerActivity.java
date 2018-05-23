package com.durian.demo.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.durian.demo.R;
import com.durian.demo.presentation.view.customviewpage.ViewPageActivity;
import com.durian.demo.presentation.view.dialview.ViewShowActivity;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/17
 */
public class ViewManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_manager);
        initView();
    }

    private void initView() {
        initDialView();
        initViewPager();
    }

    private void initDialView() {
        Button dialButton = findViewById(R.id.id_dial_view);
        dialButton.setOnClickListener(view->{
            Intent intent = new Intent(this, ViewShowActivity.class);
            startActivity(intent);
        });
    }

    private void initViewPager(){
        Button viewPager = findViewById(R.id.id_view_pager);
        viewPager.setOnClickListener(view->{
            Intent intent = new Intent(this, ViewPageActivity.class);
            startActivity(intent);
        });
    }
}
