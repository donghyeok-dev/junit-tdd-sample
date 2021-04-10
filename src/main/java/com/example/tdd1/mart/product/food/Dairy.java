package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductType;

public class Dairy extends Food {

    protected Dairy(int count, ProductType type) {
        super(count, type);
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
