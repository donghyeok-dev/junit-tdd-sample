package com.example.tdd1;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String userName;

    @Builder
    public UserDto(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
