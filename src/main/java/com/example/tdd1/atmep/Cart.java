package com.example.tdd1.atmep;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cart implements Product {
    private Product from;
    private Product to;

    public Cart(Product from, Product to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Product add(Product to) {
        return new Cart(this, to);
    }

    @Override
    public Money calculate() {
        if(from == null || to == null) {
            throw new IllegalStateException();
        }
        int amount = from.calculate().amount + to.calculate().amount;
        return Money.krw(amount);
    }
}
