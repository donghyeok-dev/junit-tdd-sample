package com.example.tdd1;

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
}
