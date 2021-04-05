package com.example.tdd1.controller;

import com.example.tdd1.dto.Dollar;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

//@SpringJUnitWebConfig
@Slf4j
class TddControllerTest {

    @Rule
    MockitoRule mockitoRule = MockitoJUnit.rule();

    TddController tddController;

    @Test
    @DisplayName("Tdd 컨트롤러를 생성한다.")
    void createController() {
         this.tddController = new TddController();
        log.info(">>> " + this.tddController.getExchangeRate());
    }

    @Test
    @DisplayName("")
    void testMultiplication() {
        Dollar five = new Dollar(5);
        assertEquals(new Dollar(10), five.times(2));
        assertEquals(new Dollar(15), five.times(3));
    }

    @Test
    @DisplayName("")
    void test2() {
        assertTrue(new Dollar(5).equals(new Dollar(5)));
        assertFalse(new Dollar(5).equals(new Dollar(6)));
    }
}