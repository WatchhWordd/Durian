package com.durian.demo.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.durian.demo.R;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/13
 */

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int margin;
    public MarginDecoration(Context context) {
        margin = context.getResources()
                .getDimensionPixelSize(R.dimen.spacing_8dp);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(margin,margin,margin,margin);
    }
}
