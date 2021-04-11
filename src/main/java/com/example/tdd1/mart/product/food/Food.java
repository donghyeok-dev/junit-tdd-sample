package com.example.tdd1.mart.product.food;

import com.example.tdd1.mart.part.CalulateResult;
import com.example.tdd1.mart.part.Cart;
import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductPrice;
import com.example.tdd1.mart.product.ProductType;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
public class Food implements Product {
    private final int count;
    private final ProductType type;

    protected Food(int count, ProductType type) {
        this.count = count;
        this.type = type;
    }

    protected boolean vaildCount(int count) {
        return count < 1;
    }
    @Override
    public Product add(Product to) {
        return new Cart(this, to);
    }

    @Override
    public CalulateResult calculate(ProductPrice productPrice) {
        return CalulateResult.singleBuilder()
                .productType(this.type)
                .count(this.count)
                .amount(this.count * productPrice.getProductPrice(this.type))
                .build();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
//        if(getClass() != o.getClass()) return false;
        int oCount = -1;
        int oCode = -1;
        if(o instanceof Food) {
            Food fruit = (Food) o;
            oCount = fruit.count;
            oCode = fruit.type.getCode();
        }else if(o instanceof Cart) {
            Cart cart = (Cart) o;
            Food from = (Food) cart.getFrom();
            Food to   = (Food) cart.getTo();
            oCount = from.count + to.count;
            oCode = from.type.getCode() == to.type.getCode() ? from.type.getCode() : -1;
        }
        return this.count == oCount && this.type.getCode() == oCode;
    }

    @Override
    public int hashCode() {
        /*  hashCode는 HashTable, HashSet, HashMap 등과 같은 자료구조에서 hashCode를 이용하기 때문에
            동일한 객체(equals가 true)라면 hashCode가 같아야 한다.
            equals를 override하면 hashCode도 override해야 된다.
            hashCode not Override => this hash: 1151844284 o.hash: 1324578393
            hashCode Override => this hash: 993 o.hash: 993
         */
        return Objects.hash(this.count, this.type.getCode());
    }
}
