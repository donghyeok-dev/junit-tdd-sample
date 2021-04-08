package com.example.tdd1.calculate;

public interface Expression {
    Money reduce(Bank bank, String to);

    Expression plus(Expression addend);

    Expression minus(Expression addend);

    Expression multiply(Expression multiplier);
}

