package com.sample.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(Extendsion1.class)
public class SampleExtendWith {

    @Test
    void test_SampleExtendWith_1() {
        System.out.println("test_SampleExtendWith_1 called!");
        Assertions.assertEquals(1, 1);
    }
}

class Extendsion1 implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("Extendsion1 beforeEach called!");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        System.out.println("Extendsion1 afterEach called!");
    }
}
