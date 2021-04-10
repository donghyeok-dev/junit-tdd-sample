package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductType;

public class Fruit extends Food {

    protected Fruit(int count, ProductType type) {
        super(count, type);
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
}
