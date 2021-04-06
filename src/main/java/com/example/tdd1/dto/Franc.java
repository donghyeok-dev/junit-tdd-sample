package com.example.tdd1.dto;

import java.util.Objects;

public class Franc extends Money {

    public Franc(int amount, String currency) {
       super(amount, currency);
    }

    public Money times(int muliplier) {
        return new Money(amount * muliplier, currency);
    }
}
