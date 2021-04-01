package com.example.tdd1;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public int calculateValue (int value) {
        System.out.println("TestService.calculateValue called!");
        return value * 2;
    }
}
