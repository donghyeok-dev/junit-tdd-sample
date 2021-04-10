package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum DairyType implements ProductType {
    MILK (21, "우유"),
    CHEESE(22, "치즈"),
    PLAIN(23, "플레인");

    private int code;
    private String name;

    DairyType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
