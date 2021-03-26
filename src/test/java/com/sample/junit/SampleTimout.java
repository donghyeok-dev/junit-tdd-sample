package com.sample.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

public class SampleTimout {

    @Test
    @Timeout(1)
    void test1() {
        Assertions.assertEquals(1, 1);
    }

    boolean asynchronousResultNotAvailable() {
        return true;
    }

    @Test
    @Timeout(5)
    void pollUntil() throws InterruptedException {
        while (asynchronousResultNotAvailable()) {
            Thread.sleep(250);
        }
    }
}
