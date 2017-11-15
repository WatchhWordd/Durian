package com.durian.demo.presentation.overview;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.data.net.bean.UserInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/9
 */

public class OverViewContract {

    public interface View extends BaseView<Presenter> {

        void showRepoesView(ArrayList<ReposInfo> reposInfos);

        void showFollowerView(ArrayList<UserInfo> userInfos);

        void showFollowingView(ArrayList<UserInfo> userInfos);

        void showStarsView(ArrayList<ReposInfo> reposInfos);

    }

    public interface Presenter extends BasePresent {

        void refreshEvents();

        void refreshOverRepoes();

        ArrayList<UserInfo> loadFollowersList();

        ArrayList<UserInfo> loadFollowingList();

        ArrayList<ReposInfo> loadReposList();

        ArrayList<ReposInfo> loadStars();
    }
}
