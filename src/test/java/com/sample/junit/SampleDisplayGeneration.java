package com.sample.junit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SampleDisplayGeneration {

    @Test
    void test_if_equals() {
        Assertions.assertEquals(1,1);
    }

    @Test
    @DisplayName("DisplayName 우선 적용")
    void test_if_true() {
        Assertions.assertTrue(true);
    }
}
