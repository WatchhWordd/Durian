package com.durian.demo.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.durian.demo.R;

/**
 * @author zhangyb
 * @description
 * @date 2018/4/23
 */
public class PaintView extends View {

    private Paint paint;
    private Bitmap bitmapSrc;
    private Bitmap bitmapDest;

    public PaintView(Context context) {
        super(context);
        paint = new Paint();
        bitmapDest = BitmapFactory.decodeResource(getResources(), R.drawable.twitter_src);
        bitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.twitter_det);
        paint.setAntiAlias(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmapDest, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(bitmapSrc, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerID);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
