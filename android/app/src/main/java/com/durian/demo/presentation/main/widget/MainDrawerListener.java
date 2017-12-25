package com.durian.demo.presentation.main.widget;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class MainDrawerListener extends ActionBarDrawerToggle {

    private DrawerLayout.DrawerListener drawerListener;

    public MainDrawerListener(Activity activity, DrawerLayout drawerLayout,
                              int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public MainDrawerListener(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                              int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public void setDrawerListener(DrawerLayout.DrawerListener drawerListener) {
        this.drawerListener = drawerListener;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        if (drawerListener != null) {
            drawerListener.onDrawerSlide(drawerView, slideOffset);
        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (drawerListener != null) {
            drawerListener.onDrawerOpened(drawerView);
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (drawerListener != null) {
            drawerListener.onDrawerClosed(drawerView);
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        super.onDrawerStateChanged(newState);
        if (drawerListener != null) {
            drawerListener.onDrawerStateChanged(newState);
        }
    }
}
