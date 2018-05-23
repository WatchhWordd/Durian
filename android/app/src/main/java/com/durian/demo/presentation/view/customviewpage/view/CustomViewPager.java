package com.durian.demo.presentation.view.customviewpage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.durian.demo.R;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class CustomViewPager extends ViewPager {

    private Bitmap bitmap;
    private int first_position = -1;

    public CustomViewPager(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_app_guide);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (first_position == -1) {
            first_position = getCurrentItem();
        }
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int count = getAdapter().getCount();
            int xScroll = getScrollX() + first_position * getWidth();

            float itemWidth = (float) (width * 1.0 / count);
            float bitmapPerPx = (float) (itemWidth * 1.0 / getWidth());

            Rect src = new Rect((int) (xScroll * bitmapPerPx), 0, (int) (xScroll * bitmapPerPx + itemWidth), height);
            Rect dest = new Rect(xScroll, 0, xScroll + getWidth(), getHeight());

            canvas.drawBitmap(bitmap, src, dest, null);
        }
        super.dispatchDraw(canvas);
    }
}
