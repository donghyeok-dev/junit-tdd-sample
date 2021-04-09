package com.example.tdd1.atmep;

import lombok.Getter;

@Getter
public class Money {
    int amount;
    String currency;

    private Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money krw(int amount) {
        return new Money(amount, "KRW");
    }
}
