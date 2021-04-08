package com.example.tdd1.calculate;

import java.util.Objects;

public class Money implements Expression{
    protected int amount;
    protected String currency;

    protected Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money reduce(Bank bank, String to) {
        int rate = bank.rate(currency, to);
        return new Money(amount /  rate, to);
    }

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Expression minus(Expression addend) {
        return new Minus(this, addend);
    }

    public Expression multiply(Expression multiplier) {
        return new Multiply(this, multiplier);
    }

    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public String currency() {
        return this.currency;
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        Money money = (Money) o;
        return amount == money.amount && currency().equals(money.currency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
