package com.example.tdd1.service;

import com.example.tdd1.dto.UserDto;
import org.springframework.stereotype.Service;


@Service
public class DemoService {

    public void getData() {
        throw new RuntimeException("aaa");
    }

    public UserDto callDBUser() {
        return null;
    }

    public int saveData(UserDto user) {

        return 1;
    }

    public String testCall(String param) {
        return param;
    }
}
