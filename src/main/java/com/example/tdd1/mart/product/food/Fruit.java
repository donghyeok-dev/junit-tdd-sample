package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductType;
import org.springframework.util.Assert;

public class Fruit extends Food {

    protected Fruit(int count, ProductType type) {
        super(count, type);
        Assert.isTrue(count > 0, "상품 개수는 0보다 커야 합니다.");
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
