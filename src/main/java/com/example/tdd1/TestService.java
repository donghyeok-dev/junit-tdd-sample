package com.example.tdd1;

import org.springframework.stereotype.Service;

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
}
