package com.example.tdd1.mart.product;

import com.example.tdd1.mart.product.food.DairyType;
import com.example.tdd1.mart.product.food.FruitType;
import com.example.tdd1.mart.product.food.MeetType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProductPrice {
    private HashMap<ProductType, Integer> price;

    public ProductPrice() {
        this.price = new HashMap<>();
        price.put(FruitType.APPLE, 2000);
        price.put(FruitType.BANANA, 1000);
        price.put(FruitType.GRAPE, 3000);
        price.put(FruitType.ORANGE, 1500);
        price.put(FruitType.TOMATO, 2000);
        price.put(MeetType.BEEF, 10000);
        price.put(MeetType.PORK, 6000);
        price.put(MeetType.CHICKEN, 5000);
        price.put(MeetType.DUCK, 7000);
        price.put(DairyType.MILK, 2700);
        price.put(DairyType.CHEESE, 2300);
        price.put(DairyType.PLAIN, 1800);
    }

    public int getProductPrice(ProductType productType) {
        return this.price.get(productType);
    }
}
