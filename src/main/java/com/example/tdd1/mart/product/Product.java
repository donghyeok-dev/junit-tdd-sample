package com.example.tdd1.mart.product;

import com.example.tdd1.mart.part.CalulateResult;

public interface Product {
    public int getCount();
    public ProductType getType();

    public Product add(Product to);

    public CalulateResult calculate(ProductPrice productPrice);
}
