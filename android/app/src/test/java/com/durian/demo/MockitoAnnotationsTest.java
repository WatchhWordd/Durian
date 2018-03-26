package com.durian.demo;

import com.durian.demo.data.net.bean.LoadParam;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author zhangyb
 * @description mockito测试
 * @date 2018/3/26
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationsTest {

    @Mock
    LoadParam loadParam;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testAssertNotNull() {
        Assert.assertNotNull(loadParam);
    }
}
