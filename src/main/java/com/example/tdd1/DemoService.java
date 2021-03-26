package com.example.tdd1;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
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
