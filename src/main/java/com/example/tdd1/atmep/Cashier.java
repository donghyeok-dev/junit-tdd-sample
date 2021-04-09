package com.example.tdd1.atmep;

import lombok.NoArgsConstructor;


public class Cashier {

    private Cashier() {
        throw new IllegalStateException("Utility Class");
    }

    public static Response checkOut(Product product, Money pay) {

        if(product == null) {
            throw new RuntimeException("계산할 상품이 없습니다.");
        }

        Money totalAmount = product.calculate();
        int change = pay.amount - totalAmount.amount;

        if(change < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        return Response.builder()
                .message("거스름돈")
                .change(Money.krw(change))
                .build();
    }
}
