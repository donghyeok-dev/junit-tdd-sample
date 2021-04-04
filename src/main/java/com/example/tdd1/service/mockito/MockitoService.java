package com.example.tdd1.service.mockito;

import com.example.tdd1.domain.User;
import com.example.tdd1.dto.MockitoDto;
import com.example.tdd1.dto.UserDto;

import java.util.List;

public interface MockitoService {
    public List<User> getUsers(MockitoDto dto);

    public List<MockitoDto> getDataList(MockitoDto dto);

    public int calculateValue(int a, int b);

    public String getTeamName(String team);

    public UserDto selectUser(String userId);

    public boolean updateUser(UserDto dto);
}
