package com.example.tdd1.dto;

import java.util.Objects;

public class Dollar {

    private int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public Dollar times(int muliplier) {
        return new Dollar(amount * muliplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dollar dollar = (Dollar) o;
        return amount == dollar.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
