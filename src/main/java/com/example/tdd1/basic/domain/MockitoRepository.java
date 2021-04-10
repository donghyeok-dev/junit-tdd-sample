package com.example.tdd1.basic.domain;

import com.example.tdd1.basic.dto.MockitoDto;

import java.util.List;


public interface MockitoRepository {
    public List<MockitoDto> getUsers(MockitoDto dto);
}
