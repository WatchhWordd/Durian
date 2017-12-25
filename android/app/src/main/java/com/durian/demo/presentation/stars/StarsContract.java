package com.durian.demo.presentation.stars;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;
import com.durian.demo.data.net.bean.ReposInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class StarsContract {
    interface View extends BaseView<Presenter> {

        void showDataListView(ArrayList<ReposInfo> reposInfos);

        void showDataFail(String fail);

    }

    interface Presenter extends BasePresent {
        void loadData(String userName);
    }
}
