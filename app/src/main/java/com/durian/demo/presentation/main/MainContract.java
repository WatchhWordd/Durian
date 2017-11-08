package com.durian.demo.presentation.main;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class MainContract {

    interface View extends BaseView<Presenter> {
        void initViews();

        void setupFragments();

        void appendFragments();
    }

    interface Presenter extends BasePresent {

    }
}
