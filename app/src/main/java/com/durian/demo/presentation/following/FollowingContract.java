package com.durian.demo.presentation.following;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;
import com.durian.demo.data.net.bean.UserInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class FollowingContract {
    interface View extends BaseView<Presenter> {

        void showDataListView(ArrayList<UserInfo> userInfos);

        void showloadFail(String fail);

    }

    interface Presenter extends BasePresent {

        void loadData(String userName);
    }
}
