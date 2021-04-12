package com.example.tdd1.mart.part;

import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductPrice;
import com.example.tdd1.mart.product.ProductType;
import com.example.tdd1.mart.product.food.Food;
import com.example.tdd1.mart.product.food.MixedType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Queue;

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

    public int getCount() {
        return this.from.getCount() + this.to.getCount();
    }

    public ProductType getType() {
        return this.from.getType().equals(this.to.getType()) ? this.from.getType() : MixedType.NO_MATCH;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        int oCount = -1;
        ProductType oCode = null;
        if(o instanceof Food) {
            Food fruit = (Food) o;
            oCount = fruit.getCount();
            oCode = fruit.getType();
        }else if(o instanceof Cart) {
            Cart cart = (Cart) o;
            Food f1 = (Food) cart.getFrom();
            Food f2   = (Food) cart.getTo();
            oCount = f1.getCount() + f2.getCount();
            oCode = f1.getType().equals(f2.getType()) ? f1.getType() : MixedType.NO_MATCH;
        }
        //this.from.getProductType().equals(this.to.getProductType()) ? this.from.getProductType().getCode() :
        return getCount() == oCount
                && oCode != MixedType.NO_MATCH
                && getType().equals(oCode);
    }

    @Override
    public int hashCode() {
        /*  hashCode는 HashTable, HashSet, HashMap 등과 같은 자료구조에서 hashCode를 이용하기 때문에
            동일한 객체(equals가 true)라면 hashCode가 같아야 한다.
            equals를 override하면 hashCode도 override해야 된다.
            hashCode not Override => this hash: 1151844284 o.hash: 1324578393
            hashCode Override => this hash: 993 o.hash: 993
         */

        return Objects.hash(getCount(), this.from.getType(), this.to.getType());
    }
}
