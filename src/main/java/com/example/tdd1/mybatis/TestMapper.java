package com.example.tdd1.mybatis;

import com.example.tdd1.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    UserDto selectUser(String userId);

    int updateUser(UserDto dto);
}
