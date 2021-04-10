package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductType;

public class Meat extends Food {

    protected Meat(int count, ProductType type) {
        super(count, type);
    }

    public static Product beef(int count) {
        return new Meat(count, MeetType.BEEF);
    }

    public static Product pork(int count) {
        return new Meat(count, MeetType.PORK);
    }

    public static Product chicken(int count) {
        return new Meat(count, MeetType.CHICKEN);
    }

    public static Product duck(int count) {
        return new Meat(count, MeetType.DUCK);
    }
}
