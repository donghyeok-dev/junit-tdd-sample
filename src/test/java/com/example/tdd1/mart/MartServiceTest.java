package com.example.tdd1.mart;

import com.example.tdd1.mart.part.Cashier;
import com.example.tdd1.mart.part.Money;
import com.example.tdd1.mart.part.Response;
import com.example.tdd1.mart.product.Product;
import com.example.tdd1.mart.product.ProductPrice;
import com.example.tdd1.mart.product.food.Dairy;
import com.example.tdd1.mart.product.food.Fruit;
import com.example.tdd1.mart.product.food.FruitType;
import com.example.tdd1.mart.product.food.Meat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MartServiceTest {
    @Test
    @DisplayName("상품 비교 테스트")
    void compare_products() {
        //given
        Product apple = Fruit.apple(1);
        Product apple2 = Fruit.apple(1);
        Product apple3 = Fruit.apple(2);
        Product banana = Fruit.banana(1);

        //then
        assertEquals(apple, apple2);
        assertNotEquals(apple, apple3);
        assertNotEquals(apple, banana);

        assertEquals(apple3, apple.add(apple));
        assertEquals(apple.add(apple), apple3);
        assertEquals(apple3, apple.add(apple2));
        assertNotEquals(apple3, apple.add(banana));
    }

    @Test
    @DisplayName("캐셔를 생성할 수 있다.")
    void can_create_cashier() {
        //given
        String cashierName = "김태희";

        //when
        Cashier cashier = Cashier.builder().cashierName(cashierName).build();

        //then
        assertNotNull(cashier);
        assertEquals(cashier.getCashierName(), cashierName);
    }

    @Test
    @DisplayName("상품코드 체크")
    void check_productType() {
        ProductPrice productPrice = new ProductPrice();

        assertEquals(2000, productPrice.getProductPrice(FruitType.APPLE));
    }

    @Test
    @DisplayName("카트에 담은 물건을 계산하여 잔돈을 받는다.")
    void add_apple_to_cart() {
        //given
        String cashierName = "김태희";
        Product cart = Fruit.apple(1)
                .add(Fruit.tomato(2))
                .add(Fruit.banana(1))
                .add(Fruit.orange(3))
                .add(Meat.beef(1))
                .add(Meat.pork(2))
                .add(Meat.duck(1))
                .add(Dairy.milk(1))
                .add(Dairy.cheese(2));

        //when
        Cashier cashier = Cashier.builder().cashierName(cashierName).build();
        Response response = cashier.checkOut(cart, Money.krw(50000));

        //then
        assertTrue(response.getChange() > 0 );

        response.printReceipt();
    }
}