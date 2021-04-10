package com.example.tdd1.mart.part;

import com.example.tdd1.mart.product.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CalulateResult{
    private StringBuilder receipt;
    private int amount;

    @Builder(builderClassName = "singleBuilder", builderMethodName = "singleBuilder")
    public CalulateResult(ProductType productType, int count, int amount) {
        this.amount = amount;
        this.receipt = new StringBuilder()
                .append(productType.getName())
                .append(" ")
                .append(count)
                .append("개 ")
                .append(amount)
                .append("원");
    }


    public CalulateResult merge(CalulateResult to) {
        this.amount += to.amount;
        this.receipt.append("\r\n").append(to.receipt);

        return this;
    }

}
