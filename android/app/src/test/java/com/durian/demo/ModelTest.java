package com.durian.demo;

import com.durian.demo.mode.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * @author zhangyb
 * @description 隔离测试
 * @date 2018/3/21
 */

public class ModelTest {

    Model model;

    @Before
    public void setUp() throws Exception {
        model = Mockito.mock(Model.class);
    }

    @Test
    public void testNativeMethod() throws Exception {
        when(model.nativeMethod()).thenReturn(true);
        Assert.assertTrue(model.nativeMethod());
    }
}
