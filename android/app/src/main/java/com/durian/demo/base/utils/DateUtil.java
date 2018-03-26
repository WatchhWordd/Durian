package com.durian.demo.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式
 */

public class DateUtil {
    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_2 = "yyyy-MM-dd";

    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse(String strDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getLastDayOfMonth() {
        Date date = new Date();
        Calendar c    = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static Date getFirstDayOfMonth() {
        Date date = new Date();
        Calendar c    = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /*
     *将时间戳转换为时间
     */
    public static String stampToDate(long lt){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_1, Locale.CHINA);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2017-11-01 22:11:00"）返回时间戳
     *
     * @param time
     * @return 时间戳
     */
    public static long dateToStamp(String time) throws ParseException{
        SimpleDateFormat sdr = new SimpleDateFormat(FORMAT_1, Locale.CHINA);
        Date date = sdr.parse(time);
        return date.getTime();
    }
}