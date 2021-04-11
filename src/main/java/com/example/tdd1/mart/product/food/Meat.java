package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductType;
import org.springframework.util.Assert;

public class Meat extends Food {

    protected Meat(int count, ProductType type) {
        super(count, type);
        Assert.isTrue(count > 0, "상품 개수는 0보다 커야 합니다.");
    }

    public static Product beef(int count) {
        return new Meat(count, MeatType.BEEF);
    }

    public static Product pork(int count) {
        return new Meat(count, MeatType.PORK);
    }

    public static Product chicken(int count) {
        return new Meat(count, MeatType.CHICKEN);
    }

    public static Product duck(int count) {
        return new Meat(count, MeatType.DUCK);
    }
}
