package com.sample.junit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SampleNested {

    @BeforeAll
    void beforeAllTest() {
        System.out.println("beforeAllTest called!");
    }

    @Test
    @DisplayName("테스트1 ")
    void test1() {
        Assertions.assertEquals(1, 1);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class InnerNestedTest1 {

        @BeforeAll
        void innerBeforeAllTest() {
            System.out.println("innerBeforeAllTest called!");
        }

        @Test
        @DisplayName("중첩테스트 1")
        public void innerTest1() {
            Assertions.assertEquals(1, 1);
        }

        @Test
        @DisplayName("중첩테스트 2")
        public void innerTest2() {
            Assertions.assertEquals(1, 1);
        }
    }

    @Nested
    class InnerNestedTest2 {
        @Test
        @DisplayName("중첩테스트 3")
        void test3() {
            Assertions.assertEquals(1, 1);
        }
    }
}
