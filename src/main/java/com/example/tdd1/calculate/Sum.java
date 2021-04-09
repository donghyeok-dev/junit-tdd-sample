package com.example.tdd1.calculate;

public class Sum implements Expression{
    Expression augend;
    Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }

    public Money reduce(Bank bank, String to) {
        int amount = augend.reduce(bank, to).amount +
                addend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    public Expression minus(Expression addend) {
        return new Minus(this, addend);
    }

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    public Expression multiply(Expression multiplier) {
        return new Multiply(this, multiplier);
    }
}