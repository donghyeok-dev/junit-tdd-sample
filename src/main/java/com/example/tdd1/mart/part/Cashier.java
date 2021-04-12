package com.example.tdd1.mart.part;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductPrice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Queue;

@Getter
@NoArgsConstructor
public class Cashier {
    private String cashierName;

    @Builder
    public Cashier(String cashierName) {
        this.cashierName = cashierName;
    }

    public Response checkOut(Product product, Money pay) {
        Assert.notNull(product, "계산할 상품이 없습니다.");

        ProductPrice productPrice = new ProductPrice();
        CalulateResult result = product.calculate(productPrice);

        int change = pay.amount - result.getAmount(); //거스름돈
        Assert.isTrue(change > 0, "잔액이 부족합니다.");

        return Response.builder()
                .cashierName(this.getCashierName())
                .receipt(result.getReceipt().toString())
                .pay(pay.amount)
                .totalAmount(result.getAmount())
                .change(change)
                .build();
    }
}
