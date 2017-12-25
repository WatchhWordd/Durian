package com.durian.demo.base.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/15
 */

public class ScreenUtil {

    /**
     * @param context     上下文
     * @param originWidth 原始的页面宽度
     * @see
     */
    public static int getWebViewScale(Context context, int originWidth) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width) / new Double(originWidth);
        val = val * 100d;
        return val.intValue();
    }
}
