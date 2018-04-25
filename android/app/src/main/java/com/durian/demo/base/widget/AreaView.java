package com.durian.demo.base.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/10
 */
public class AreaView extends View {

    private Paint paint;
    private Path path;
    private int width;
    private int height;
    private int lineX = 0;
    private int lineY = 200;
    private int mItemWaveLength = 400;
    private int dx;

    public AreaView(Context context) {
        super(context);
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        path = new Path();
//        path.moveTo(100,300);
//        path.rQuadTo(100,-100,200,0);
//        path.rQuadTo(100,100,200,0);
//        canvas.drawPath(path,paint);
        drawQuad(canvas);
    }

    private void drawQuad(Canvas canvas) {
        path.reset();
        int originY = getHeight() / 2;
        int halfWaveLen = mItemWaveLength / 2;
        path.moveTo(-mItemWaveLength + dx, originY);
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            path.rQuadTo(halfWaveLen / 2, -100, halfWaveLen, 0);
            path.rQuadTo(halfWaveLen / 2, 100, halfWaveLen, 0);
        }

        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawCharect(Canvas canvas) {
        paint.setColor(Color.GREEN);
        paint.setTextSize(120);
        canvas.drawText("harvic\'s blog", lineX, lineY, paint);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float ascent = fontMetrics.ascent + lineY;
        float descent = fontMetrics.descent + lineY;
        float top = fontMetrics.top + lineY;
        float bottom = fontMetrics.bottom + lineY;

        paint.setColor(Color.RED);
        canvas.drawLine(lineX, lineY, 3000, lineY, paint);

        paint.setColor(Color.BLUE);
        canvas.drawLine(lineX, top, 3000, top, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(lineX, ascent, 3000, ascent, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawLine(lineX, descent, 3000, descent, paint);

        paint.setColor(Color.RED);
        canvas.drawLine(lineX, bottom, 3000, bottom, paint);
    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect r = new Rect();
        while (regionIterator.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
