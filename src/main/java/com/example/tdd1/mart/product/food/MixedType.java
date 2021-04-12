package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum MixedType implements ProductType {
    NO_MATCH (-1, "혼합");

    private int code;
    private String name;

    MixedType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
