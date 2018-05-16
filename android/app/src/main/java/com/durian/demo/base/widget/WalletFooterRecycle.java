package com.durian.demo.base.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.durian.demo.base.listener.ScrollViewListener;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/27
 */
public class WalletFooterRecycle extends RecyclerView {

    private OnScrollListener onScrollListener;
    private ScrollViewListener scrollViewListener;
    public WalletFooterRecycle(Context context) {
        super(context);
    }
}
