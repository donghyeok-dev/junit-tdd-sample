package com.sample.junit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

public class SampleRepeatTest {

    @RepeatedTest(3)
    @DisplayName("반복 테스트")
    void testMethod1() {
        Assertions.assertEquals(1, 1);
    }

    @RepeatedTest(3)
    void testMethod2(RepetitionInfo repetitionInfo) {
        Assertions.assertTrue(repetitionInfo.getCurrentRepetition() <= repetitionInfo.getTotalRepetitions());
    }

    @RepeatedTest(value = 3, name="{displayName} {currentRepetition} of {totalRepetitions}")
    @DisplayName("JUnit5 반복 테스트")
    void testMethod3(TestInfo testinfo) {
        Assertions.assertTrue(Objects.nonNull(testinfo.getDisplayName()));
    }
}
