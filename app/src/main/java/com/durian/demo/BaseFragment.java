package com.durian.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhangyb
 * @description fragment父类
 * @date 2017/11/9
 */

public abstract class BaseFragment extends Fragment {

    public Context context;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(getLayoutId(), container, false);
            initView(view);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public abstract int getLayoutId();

    public abstract void initView(View view);

    public abstract void initData();
}
