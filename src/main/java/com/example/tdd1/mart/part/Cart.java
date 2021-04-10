package com.example.tdd1.mart.part;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductPrice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Cart implements Product {
    private Product from;
    private Product to;

    public Cart(Product from, Product to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Product add(Product to) {
        return new Cart(this, to);
    }

    @Override
    public CalulateResult calculate(ProductPrice productPrice) {
        if (from == null || to == null) {
            throw new IllegalStateException();
        }

        return from.calculate(productPrice).merge(to.calculate(productPrice));
    }
}
