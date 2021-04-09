package com.example.tdd1.atmep;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum FruitType {
    APPLE (1, "사과"),
    BANANA(2, "바나나"),
    ORANGE(3, "오렌지"),
    GRAPE (4, "포도"),
    TOMATO(5, "토마토");

    private int code;
    private String name;

    FruitType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
