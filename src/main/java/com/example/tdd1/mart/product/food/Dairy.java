package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductType;
import org.springframework.util.Assert;

public class Dairy extends Food {

    protected Dairy(int count, ProductType type) {
        super(count, type);
        Assert.isTrue(count > 0, "상품 개수는 0보다 커야 합니다.");
    }

    public static Product milk(int count) {
        return new Dairy(count, DairyType.MILK);
    }

    public static Product cheese(int count) {
        return new Dairy(count, DairyType.CHEESE);
    }

    public static Product plain(int count) {
        return new Dairy(count, DairyType.PLAIN);
    }
}
