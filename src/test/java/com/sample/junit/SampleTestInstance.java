package com.sample.junit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SampleTestInstance.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SampleTestInstance {
    @BeforeAll
    void beforeAllTest() {
        System.out.println(">>> beforeAllTest called");
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println(">>> beforeEachTest called");
    }

    @Test
    void test1() {
        System.out.println(">>> test1 called");
        Assertions.assertEquals(1, 1);
    }

    @Test
    void test2() {
        System.out.println(">>> test2 called");
        Assertions.assertEquals(1, 1);
    }

    @Nested
    class NestClass {

        @BeforeEach
        void nestBeforeAll() {
            System.out.println(">>> nestBeforeAll called");
        }

        @Test
        void nest_test1() {
            System.out.println(">>> nest_test1 called");
            Assertions.assertEquals(1, 1);
        }
    }

    @AfterEach
    void afterEachTest() {
        System.out.println(">>> afterEachTest called");
    }

    @AfterAll
    void afterAllTest() {
        System.out.println(">>> afterAllTest called");
    }


}
