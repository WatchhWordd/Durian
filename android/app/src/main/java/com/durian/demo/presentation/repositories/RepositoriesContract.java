package com.durian.demo.presentation.repositories;

import com.durian.demo.BasePresent;
import com.durian.demo.BaseView;
import com.durian.demo.data.net.bean.ReposInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/9
 */

public class RepositoriesContract {

    interface View extends BaseView<Presenter> {

        void showRepoDataList(ArrayList<ReposInfo> reposInfos);

        void showRepoDataFail(Throwable throwable);
    }

    interface Presenter extends BasePresent {
        void loadRepoData(String userName);
    }
}
