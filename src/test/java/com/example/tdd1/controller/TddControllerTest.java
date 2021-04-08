package com.example.tdd1.controller;

import com.example.tdd1.calculate.Bank;
import com.example.tdd1.calculate.Expression;
import com.example.tdd1.calculate.Money;
import com.example.tdd1.calculate.Sum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
        Money five = Money.franc(5);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
    }

    @Test
    @DisplayName("")
    void testEquality() {
        assertEquals(Money.dollar(5), Money.dollar(5));
        assertNotEquals(Money.dollar(6), Money.dollar(5));
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
    void testReduceSum() {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7), result);
    }

    @Test
    @DisplayName("")
    public void testReduceMoney() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        assertEquals(Money.dollar(1), result);
    }

    @Test
    @DisplayName("")
    void testIdentityRate() {
        assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    @DisplayName("")
    void testMixedAddition() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
        assertEquals(Money.dollar(10), result);
    }

    @Test
    @DisplayName("")
    void testSumplusMoney() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = Money.dollar(5)
                .plus(Money.franc(10))
                .plus(Money.dollar(5))
                .plus(Money.dollar(5))
                .minus(Money.dollar(15))
                .multiply(Money.dollar(2));

        Money result = bank.reduce(sum, "USD");

        assertEquals(Money.dollar(10), result);
    }
}