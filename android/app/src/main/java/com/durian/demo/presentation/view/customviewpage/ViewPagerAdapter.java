package com.durian.demo.presentation.view.customviewpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.durian.demo.presentation.view.customviewpage.fragment.ViewPageFragmentOne;
import com.durian.demo.presentation.view.customviewpage.fragment.ViewPageFragmentThree;
import com.durian.demo.presentation.view.customviewpage.fragment.ViewPageFragmentTwo;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/17
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ViewPageFragmentOne();
                break;
            case 1:
                fragment = new ViewPageFragmentTwo();
                break;
            case 2:
                fragment = new ViewPageFragmentThree();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
