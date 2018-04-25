package com.durian.demo.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @author zhangyb
 * @description 实现拖动小圆点效果
 * @date 2018/4/25
 */
public class RedPointView extends FrameLayout {

    private PointF startPoint;
    private PointF endPoint;
    private int radius = 30;
    private Paint paint;
    private Path path;
    private Boolean isTouch = false;

    public RedPointView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        startPoint = new PointF(100, 100);
        endPoint = new PointF();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        path = new Path();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint);
        canvas.drawCircle(startPoint.x, startPoint.y, radius, paint);
        if (isTouch) {
            cacluatePath();
            canvas.drawCircle(endPoint.x, endPoint.y, radius, paint);
            canvas.drawPath(path,paint);
        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }

        endPoint.set(event.getX(), event.getY());
        postInvalidate();
        return true;
    }

    private void cacluatePath() {

        float startX = startPoint.x;
        float startY = startPoint.y;
        float endX = endPoint.x;
        float endY = endPoint.y;

        float dx = endX - startX;
        float dy = endY - startY;

        double degree = Math.atan(dy / dx);
        float offsetX = (float) (radius * Math.sin(degree));
        float offsetY = (float) (radius * Math.cos(degree));

        // 根据角度算出四边形的四个点
        float x1 = startX + offsetX;
        float y1 = startY - offsetY;

        float x2 = endX + offsetX;
        float y2 = endY - offsetY;

        float x3 = endX - offsetX;
        float y3 = endY + offsetY;

        float x4 = startX - offsetX;
        float y4 = startY + offsetY;

        float anchorX = (startX + endX) / 2;
        float anchorY = (startY + endY) / 2;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);
    }
}
