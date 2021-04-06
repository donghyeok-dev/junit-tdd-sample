package com.example.tdd1.dto;

import java.util.Objects;

public class Dollar extends Money {

    public Dollar(int amount, String currency) {
        super(amount, currency);
    }

    public Money times(int muliplier) {
        return new Money(amount * muliplier, currency);
    }

}
