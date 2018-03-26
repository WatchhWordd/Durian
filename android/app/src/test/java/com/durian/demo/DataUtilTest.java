package com.durian.demo;

import com.durian.demo.base.utils.DateUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author zhangyb
 * @description 时间格式测试
 * @date 2018/3/20
 */

public class DataUtilTest {

    private String dataFormat="2018-03-20 10:31:33";
    private Date date;
    private long dateLong = 1521513093000L;

    @Before
    public void setUp(){
        System.out.print("开始");
        date = new Date();
        date.setTime(dateLong);
    }

    @After
    public void tearDown(){
        System.out.print("结束");
    }

    @Test
    public void formatTime(){
        System.out.print("formatTime");
        Assert.assertEquals(dataFormat, DateUtil.stampToDate(dateLong));
    }

    @Test
    public void testFormatTime() throws ParseException {
        System.out.print("testFormatTime");
        Assert.assertEquals(dateLong,DateUtil.dateToStamp(dataFormat));
    }
}
