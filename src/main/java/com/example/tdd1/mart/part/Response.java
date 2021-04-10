package com.example.tdd1.mart.part;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
public class Response {
    private String cashierName;
    private int pay;
    private int totalAmount;
    private int change;
    private String receipt;
    private Date createTime;

    @Builder
    public Response(String cashierName, String receipt, int pay, int totalAmount, int change) {
        this.cashierName = cashierName;
        this.receipt = receipt;
        this.pay = pay;
        this.totalAmount = totalAmount;
        this.change = change;
        this.createTime = new Date();
    }

    public void printReceipt() {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

        System.out.println("==================== 영수증 ====================");
        System.out.println(format.format(this.createTime) + " 캐셔: " + this.cashierName);
        System.out.println("------------------------------------------------");
        System.out.println(this.receipt);
        System.out.println("------------------------------------------------");
        System.out.println("- 합  계 : " + this.totalAmount + "원");
        System.out.println("- 받은돈 : " + this.pay + "원");
        System.out.println("- 거스름 : " + this.change + "원");
        System.out.println("================================================");
    }
}
