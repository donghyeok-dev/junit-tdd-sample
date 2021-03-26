package com.example.tdd1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@SpringJUnitWebConfig
class DemoServiceTest {
    @Autowired
    DemoService demoService;

    @Test
    void test1() {
        String result = demoService.testCall("hello");
        Assertions.assertEquals(result, "hello");
    }
}