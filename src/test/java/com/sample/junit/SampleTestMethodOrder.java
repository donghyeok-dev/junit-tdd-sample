package com.sample.junit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SampleTestMethodOrder {
    @Test
    @Order(3)
    @DisplayName("첫번째 메소드")
    public void firstMethod() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    @Order(1)
    @DisplayName("두번째 메소드")
    public void secondMethod() {
        Assertions.assertEquals(1, 1);
    }
}
