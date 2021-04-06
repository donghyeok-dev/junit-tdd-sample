package com.example.tdd1.controller;

import com.example.tdd1.dto.Dollar;
import com.example.tdd1.dto.Franc;
import com.example.tdd1.dto.Money;
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

//    @Test
//    @DisplayName("Tdd 컨트롤러를 생성한다.")
//    void createController() {
//         this.tddController = new TddController();
//        log.info(">>> " + this.tddController.getExchangeRate());
//    }

    @Test
    @DisplayName("")
    void testMultiplication() {
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    @Test
    @DisplayName("")
    void testFrancMultiplication() {
        Franc five = Money.franc(5);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
    }

    @Test
    @DisplayName("")
    void testEquality() {
        assertEquals(Money.dollar(5), Money.dollar(5));
        assertNotEquals(Money.dollar(6), Money.dollar(5));
        assertEquals(Money.franc(5), Money.franc(5));
        assertNotEquals(Money.franc(6), Money.franc(5));
        assertNotEquals(Money.franc(5), Money.dollar(5));
    }

    @Test
    @DisplayName("")
    void testCurrency() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    @Test
    @DisplayName("")
    void testDifferentClassEquality() {
        assertEquals(new Money(10, "CHF"), new Franc(10, "CHF"));
    }
}