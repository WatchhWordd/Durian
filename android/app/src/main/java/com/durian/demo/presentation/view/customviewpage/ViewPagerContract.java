package com.durian.demo.presentation.view.customviewpage;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class ViewPagerContract {

    interface View extends BaseView<Presenter>{

    }

    interface  Presenter extends BasePresent{

    }
}
