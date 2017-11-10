package com.durian.demo.presentation.stars;

import android.content.Context;

import com.durian.demo.domain.usecase.GetStarsList;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/10
 */

public class StarsPresenter implements StarsContract.Presenter {

    private Context context;
    private StarsContract.View starsView;
    private GetStarsList getStarsList;

    public StarsPresenter(Context context, StarsContract.View view, GetStarsList getStarsList) {
        this.context = context;
        this.starsView = view;
        this.getStarsList = getStarsList;
        starsView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
