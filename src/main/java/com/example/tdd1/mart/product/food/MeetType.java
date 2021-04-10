package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.product.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum MeetType implements ProductType {
    BEEF(11, "소고기"),
    PORK(12, "돼지고기"),
    CHICKEN(13, "닭고기"),
    DUCK(14, "오리고기");

    private int code;
    private String name;

    MeetType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
