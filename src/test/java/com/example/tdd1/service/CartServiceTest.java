package com.example.tdd1.service;

import com.example.tdd1.atmep.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    @Test
    @DisplayName("카트에 담은 물건을 계산하여 잔돈을 받는다.")
    void add_apple_to_cart() {
        //given
        Product product = Fruit.apple(1)
                .add(Fruit.tomato(2))
                .add(Fruit.banana(1))
                .add(Fruit.orange(3));

        //when
        Response response = Cashier.checkOut(product, Money.krw(10000));

        //then
        assertEquals(7000, response.getChange().getAmount());
    }
}