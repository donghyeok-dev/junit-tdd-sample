package com.example.tdd1.atmep;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Fruit implements Product {
    private int count=0;
    private FruitType type;

    private Fruit(int count, FruitType type) {
        this.count = count;
        this.type = type;
    }

    public static Product apple(int count) {
        return new Fruit(count, FruitType.APPLE);
    }

    public static Product banana(int count) {
        return new Fruit(count, FruitType.BANANA);
    }

    public static Product orange(int count) {
        return new Fruit(count, FruitType.ORANGE);
    }

    public static Product grape(int count) {
        return new Fruit(count, FruitType.GRAPE);
    }

    public static Product tomato(int count) {
        return new Fruit(count, FruitType.TOMATO);
    }

    @Override
    public Product add(Product to) {
        return new Cart(this, to);
    }

    @Override
    public Money calculate() {
        return Money.krw(this.count * 1000);
    }

}
