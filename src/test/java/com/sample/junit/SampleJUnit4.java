package com.sample.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SampleJUnit4 {

    @Test
    public void junit4Test() {
        Assert.assertEquals(1, 1);
    }
}
