package com.durian.demo.presentation.view.dialview.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.durian.demo.R;
import com.durian.demo.base.utils.ScreenUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangyb
 * @description 芝麻信用表盘
 * @date 2018/4/26
 */
public class ZhiFuBaoCircle extends View {


    private final static int defaultPadding = 20;// 默认Padding值
    private final static float startAngle = 165f; //  圆环起始角度
    private final static float endAngle = 210f;// 圆环结束角度
    // 圆环的信用等级文本
    String[] sesameStr = new String[]{
            "350", "较差",
            "550", "中等",
            "600", "良好",
            "650", "优秀",
            "700", "极好",
            "950"
    };
    private int minNum = 0;// 最小数字
    private int maxNum = 950;// 最大数字
    private float currentAngle = 0f;
    private float totalAngle = 210f;
    private String sesameLevel = "";   //信用等级
    private String evaluationTime = "";//评估时间
    private int defaultSize;  // 默认宽高值
    private int destCircle;
    private int width;
    private int height;
    private int radius;
    private float[] tan;
    private float[] pos;
    private Matrix matrix;
    private Paint innerPaint;
    private Paint outPaint;
    private Paint midTextPaint;
    private Paint scaleSmallPaint;
    private Paint scaleBigPaint;
    private Paint scaleTextPaint;
    private Paint progressPaint;
    private Bitmap bitmapCircle;
    private Paint smallCirclePaint;
    private RectF innerRect;
    private RectF outRect;
    private RectF progressRect;

    public ZhiFuBaoCircle(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        defaultSize = ScreenUtil.dp2px(context, 300);
        destCircle = ScreenUtil.dp2px(context, 12);
        //外层圆环画笔
        outPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outPaint.setStrokeWidth(8);
        outPaint.setColor(Color.WHITE);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setAlpha(80);

        //内层圆环画笔
        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStrokeWidth(30);
        innerPaint.setColor(Color.WHITE);
        innerPaint.setAlpha(80);
        innerPaint.setStyle(Paint.Style.STROKE);

        //正中间字体画笔
        midTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        midTextPaint.setColor(Color.WHITE);
        midTextPaint.setTextAlign(Paint.Align.CENTER);

        //圆环大刻度画笔
        scaleBigPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleBigPaint.setStrokeWidth(4);
        scaleBigPaint.setStyle(Paint.Style.STROKE);
        scaleBigPaint.setColor(Color.WHITE);
        scaleBigPaint.setAlpha(120);

        //圆环小刻度画笔
        scaleSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleSmallPaint.setStrokeWidth(1);
        scaleSmallPaint.setStyle(Paint.Style.STROKE);
        scaleSmallPaint.setColor(Color.WHITE);
        scaleSmallPaint.setAlpha(130);

        //转盘文字画笔
        scaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleTextPaint.setTextSize(30);
        scaleTextPaint.setColor(Color.WHITE);

        //外层进度画笔
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStrokeWidth(8);
        progressPaint.setColor(Color.WHITE);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallCirclePaint.setStyle(Paint.Style.FILL);
        bitmapCircle = BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle);

        pos = new float[2];
        tan = new float[2];
        matrix = new Matrix();
    }

    public ZhiFuBaoCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = width / 2;

        outRect = new RectF(defaultPadding, defaultPadding,
                width - defaultPadding, height - defaultPadding);
        innerRect = new RectF(defaultPadding + destCircle, defaultPadding + destCircle,
                width - defaultPadding - destCircle, height - defaultPadding - destCircle);

        progressRect = new RectF(defaultPadding, defaultPadding,
                width - defaultPadding, height - defaultPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOutCircle(canvas);
        drawInnerCircle(canvas);
        drawScaleSmall(canvas);
        drawScaleBig(canvas);
        drawMidText(canvas);
        drawProgress(canvas);
    }

    private void drawOutCircle(Canvas canvas) {
        canvas.drawArc(outRect, startAngle, endAngle, false, outPaint);
    }

    private void drawInnerCircle(Canvas canvas) {
        canvas.drawArc(innerRect, startAngle, endAngle, false, innerPaint);
    }

    private void drawScaleSmall(Canvas canvas) {
        canvas.save();
        //逆时针旋转105°，以中心点radius为中心点
        canvas.rotate(-105, radius, radius);
        int startIndex = (int) (defaultPadding + destCircle - innerPaint.getStrokeWidth() / 2);
        int endIndex = (int) (startIndex + innerPaint.getStrokeWidth() / 2);

        for (int i = 0; i < 35; i++) {
            canvas.drawLine(radius, startIndex, radius, endIndex, scaleSmallPaint);
            canvas.rotate(6, radius, radius);
        }
        canvas.restore();
    }

    private void drawScaleBig(Canvas canvas) {
        canvas.save();
        canvas.rotate(-105, radius, radius);
        int startIndex = (int) (defaultPadding + destCircle - innerPaint.getStrokeWidth() / 2);
        int endIndex = (int) (startIndex + innerPaint.getStrokeWidth());
        int rotateAngle = 210 / 10;
        for (int i = 1; i < 12; i++) {
            if (i % 2 != 0) {
                canvas.drawLine(radius, startIndex, radius, endIndex, scaleBigPaint);
            }
            // 测量文本的长度
            float textLen = scaleTextPaint.measureText(sesameStr[i - 1]);
            canvas.drawText(sesameStr[i - 1], radius - textLen / 2, endIndex + 40, scaleTextPaint);
            canvas.rotate(rotateAngle, radius, radius);
        }
        canvas.restore();
    }

    private void drawMidText(Canvas canvas) {
        //绘制Logo
        midTextPaint.setTextSize(30);
        canvas.drawText("BETA", radius, radius - 130, midTextPaint);

        //绘制信用分数
        midTextPaint.setTextSize(200);
        midTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(String.valueOf(maxNum), radius, radius + 70, midTextPaint);

        //绘制信用级别
        midTextPaint.setTextSize(80);
        canvas.drawText(sesameLevel, radius, radius + 160, midTextPaint);

        //绘制评估时间
        midTextPaint.setTextSize(30);
        canvas.drawText(evaluationTime, radius, radius + 205, midTextPaint);
    }

    private void drawProgress(Canvas canvas) {
        Path path = new Path();
        path.addArc(progressRect, startAngle, currentAngle);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, pos, tan);
        matrix.reset();
        matrix.postTranslate(pos[0] - bitmapCircle.getWidth() / 2, pos[1] - bitmapCircle.getHeight() / 2);
        canvas.drawPath(path, progressPaint);
        //起始角度不为0时候才进行绘制小圆点
        if (currentAngle == 0) {
            return;
        }
        canvas.drawBitmap(bitmapCircle, matrix, smallCirclePaint);
        smallCirclePaint.setColor(Color.WHITE);
        canvas.drawCircle(pos[0], pos[1], 8, smallCirclePaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveCurrentSize(widthMeasureSpec, defaultSize),
                resolveCurrentSize(heightMeasureSpec, defaultSize));
    }

    private int resolveCurrentSize(int measureSpecSize, int defaultSize) {
        int result;
        int size = MeasureSpec.getSize(measureSpecSize);
        switch (MeasureSpec.getMode(measureSpecSize)) {
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                result = defaultSize;
                break;
        }
        return result;
    }

    public void setSesameValues(int values) {

        if (values <= 350) {
            maxNum = values;
            totalAngle = 0f;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 550) {
            maxNum = values;
            totalAngle = (values - 350) * 80 / 400f + 2;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 700) {
            maxNum = values;
            if (values > 550 && values <= 600) {
                sesameLevel = "信用中等";
                totalAngle = (values - 550) * 120 / 150f + 43;
            } else if (values > 600 && values <= 650) {
                sesameLevel = "信用良好";
                totalAngle = (values - 550) * 120 / 150f + 45;
            } else {
                sesameLevel = "信用优秀";
                totalAngle = (values - 550) * 120 / 150f + 48;
            }
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 950) {
            maxNum = values;
            totalAngle = (values - 700) * 40 / 250f + 170;
            sesameLevel = "信用极好";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else {
            totalAngle = 240f;
        }
        startAnimation();
    }

    public String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy:MM:dd");
        return format.format(new Date());
    }

    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentAngle, totalAngle);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(animation -> {
            currentAngle = (float) animation.getAnimatedValue();
            postInvalidate();
        });
        valueAnimator.start();

        ValueAnimator textAnimator = ValueAnimator.ofInt(minNum, maxNum);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            minNum = Math.round((Float) animation.getAnimatedValue());
            postInvalidate();
        });
        textAnimator.start();
    }
}
