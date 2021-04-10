package com.example.tdd1.mart.part;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductPrice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Cashier {
    private String cashierName;

    @Builder
    public Cashier(String cashierName) {
        this.cashierName = cashierName;
    }

    public Response checkOut(Product product, Money pay) {

        if(product == null) {
            throw new RuntimeException("계산할 상품이 없습니다.");
        }

        ProductPrice productPrice = new ProductPrice();

        CalulateResult result = product.calculate(productPrice);

        int change = pay.amount - result.getAmount(); //거스름돈

        if(change < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        return Response.builder()
                .cashierName(this.getCashierName())
                .receipt(result.getReceipt().toString())
                .pay(pay.amount)
                .totalAmount(result.getAmount())
                .change(change)
                .build();
    }
}
