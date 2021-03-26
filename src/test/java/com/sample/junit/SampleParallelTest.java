package com.sample.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class SampleParallelTest {

    @Test
    void test1() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test2() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test3() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test4() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test5() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test6() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test7() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test8() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test9() throws InterruptedException  {
        Thread.sleep(1000);
        Assertions.assertEquals(1, 1);
    }


}
