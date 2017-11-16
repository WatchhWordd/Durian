package com.durian.demo.presentation.overview;

import android.content.Context;

import com.durian.demo.base.utils.ACache;
import com.durian.demo.base.utils.ConfigUtil;
import com.durian.demo.base.utils.RxBus;
import com.durian.demo.data.net.bean.RefreshParam;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.data.net.bean.UserInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/9
 */

public class OverViewPresenter implements OverViewContract.Presenter {

    private OverViewContract.View overView;
    private Context context;


    public OverViewPresenter(Context context, OverViewContract.View overView) {
        this.context = context;
        this.overView = overView;
        this.overView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshEvents() {

    }

    @Override
    public void refreshOverRepoes() {
        RefreshParam refreshParam = new RefreshParam();
        RxBus.getInstance().post(refreshParam);
    }

    @Override
    public ArrayList<UserInfo> loadFollowersList() {
        Object object = ACache.get(context).getAsObject(ConfigUtil.S_FOLLOWERS);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<UserInfo>) object;
        }
    }

    @Override
    public ArrayList<UserInfo> loadFollowingList() {
        Object object = ACache.get(context).getAsObject(ConfigUtil.S_FOLLOWINGS);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<UserInfo>) object;
        }
    }

    @Override
    public ArrayList<ReposInfo> loadReposList() {
        Object object = ACache.get(context).getAsObject(ConfigUtil.S_REPOES);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<ReposInfo>) object;
        }
    }

    @Override
    public ArrayList<ReposInfo> loadStars() {
        Object object = ACache.get(context).getAsObject(ConfigUtil.S_STARRED);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<ReposInfo>) object;
        }
    }
}
