package com.example.tdd1.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DemoDto {
    private String userId;
    private String userPassword;
    private String userName;
    private int age;

    @Builder
    public DemoDto(String userId, String userPassword, String userName, int age) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.age = age;
    }
}
