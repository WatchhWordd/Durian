package com.durian.demo.base.widget;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewConfiguration;

import com.durian.demo.base.listener.ScrollViewListener;
import com.durian.demo.base.utils.ScreenUtil;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/27
 */
public class WalletHeadRecycle extends RecyclerView {

    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
    // Fling friction
    private static float flingFriction = ViewConfiguration.getScrollFriction();
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private static double physicalCoffe;
    private int itemWidth;
    private int mSelected = -1;
    private LinearLayoutManager linearLayoutManager;
    private OnSelectListener onSelectListener;
    private ScrollViewListener scrollViewListener;

    public WalletHeadRecycle(Context context) {
        super(context);
        itemWidth = ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(context, 60);
        initView(context);
    }

    private void initView(Context context) {
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(linearLayoutManager);
        addOnScrollListener(new WalletScrollListener());
    }

    public ScrollViewListener getScrollViewListener() {
        return scrollViewListener;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnScrollListener(OnSelectListener onScrollListener) {
        this.onSelectListener = onScrollListener;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        physicalCoffe = (float) (SensorManager.GRAVITY_EARTH
                * 0.38
                * getResources().getDisplayMetrics().density * 160
                * 0.84f);

        int v;
        int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (Math.abs(velocityX) <= 3 * touchSlop) {
            return false;
        }

        int firstVisiblePos = linearLayoutManager.findFirstVisibleItemPosition();
        if (firstVisiblePos == RecyclerView.NO_POSITION) {
            return false;
        }

        Rect rect = new Rect();
        linearLayoutManager.findViewByPosition(firstVisiblePos).getHitRect(rect);
        double n = getSplineFlingDistance(velocityX) / itemWidth;
        int num = Double.valueOf(n).intValue();

        //把double转化为int,通过整数num求出恰好滑动整数个item的初始速率
        if (velocityX > 0)
            v = Double.valueOf(getVelocityByDistance(num * itemWidth + Math.abs(rect.right) - ScreenUtil.dp2px(getContext(), 20))).intValue();
        else
            v = Double.valueOf(getVelocityByDistance(num * itemWidth + Math.abs(rect.left) + ScreenUtil.dp2px(getContext(), 20))).intValue();
        if (velocityX < 0) v = -v;
        return super.fling(v, velocityY);
    }

    //通过初始速度获取最终滑动距离
    private double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return flingFriction * physicalCoffe * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    //通过需要滑动的距离获取初始速度
    public static int getVelocityByDistance(double distance) {
        final double l = getSplineDecelerationByDistance(distance);
        int velocity = (int) (Math.exp(l) * flingFriction * physicalCoffe / INFLEXION);
        return Math.abs(velocity);
    }

    private double getSplineDeceleration(int velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (flingFriction * physicalCoffe));
    }

    private static double getSplineDecelerationByDistance(double distance) {
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return decelMinusOne * (Math.log(distance / (flingFriction * physicalCoffe))) / DECELERATION_RATE;
    }

    public interface OnSelectListener {
        void onSelect(int position);
    }

    private class WalletScrollListener extends OnScrollListener {

        public WalletScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int selected;
                //当控件停止滚动时，获取可视范围第一个item的位置，滚动调整控件以使选中的item刚好处于正中间
                int firstVisiblePos = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisiblePos == RecyclerView.NO_POSITION) {
                    return;
                }
                Rect rect = new Rect();
                linearLayoutManager.findViewByPosition(firstVisiblePos).getHitRect(rect);
                if (rect.left == 0) return;
                if (Math.abs(rect.left) > itemWidth / 2) {
                    smoothScrollBy(rect.right - ScreenUtil.dp2px(getContext(), 20), 0);
                    selected = firstVisiblePos + 1;
                } else {
                    smoothScrollBy(rect.left - ScreenUtil.dp2px(getContext(), 20), 0);
                    selected = firstVisiblePos;
                }
                //回弹结束后的回调
                if (Math.abs(rect.left) == 0 && onSelectListener != null && selected != mSelected) {
                    mSelected = selected;
                    onSelectListener.onSelect(mSelected);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
