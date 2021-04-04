package com.example.tdd1.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("UserDto")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userName;

    @Builder
    public UserDto(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
