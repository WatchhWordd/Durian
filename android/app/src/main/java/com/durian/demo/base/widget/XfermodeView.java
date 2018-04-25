package com.durian.demo.base.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @author zhangyb
 * @description 实现重叠效果
 * @date 2018/4/25
 */
public class XfermodeView extends View {

    private Paint paint;
    private Bitmap destBitmap;
    private Bitmap srcBitmap;
    private int width = 400;
    private int height = 400;

    public XfermodeView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setAntiAlias(true);
        destBitmap = createDestBitmap(width, height);
        srcBitmap = createSrcBitmap(width, height);
    }

    private Bitmap createDestBitmap(int width, int height) {
        paint.setColor(0xFFFFCC44);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawOval(new RectF(0, 0, width, height), paint);
        return bitmap;
    }

    private Bitmap createSrcBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(0xFF66AAFF);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawRect(new RectF(0, 0, width, height), paint);
        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GREEN);
        int layerID = canvas.saveLayer(new RectF(0, 0, width * 2, height * 2), paint);

        canvas.drawBitmap(destBitmap,0,0,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap,width/2,height/2,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerID);
    }
}
