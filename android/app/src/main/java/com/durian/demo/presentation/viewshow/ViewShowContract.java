package com.durian.demo.presentation.viewshow;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/19
 */
public class ViewShowContract {

    interface View extends BaseView<ViewShowContract.Presenter>{

    }

    interface  Presenter extends BasePresent{

    }
}
