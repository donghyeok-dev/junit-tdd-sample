package com.example.tdd1.basic.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TestService {

    public int multiply (int value) {
        System.out.println(">>>*** TestService.multiply called! value: " + value);
        return 2 * value;
    }

    public int division (int value) {
        System.out.println(">>>/// TestService.division called! value: " + value);
        return 2 / value;
    }

    public void getMessage(int value) {
        System.out.println(">>>doNothing called: " +  String.format("%s 만큼 이동하셨습니다.", value));
    }

    public String getTeamName(String team) {
        System.out.println(">>>>>>> getTeamName get call");
        return team + " Team";
    }

    public Integer dynamicSum(Integer ... values) {
        System.out.println(">>> dynamicSum called!");
        return Arrays.stream(values).reduce(0, Integer::sum);
    }
}
