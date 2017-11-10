package com.durian.demo.presentation.stars;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class StarsContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresent {
    }
}
