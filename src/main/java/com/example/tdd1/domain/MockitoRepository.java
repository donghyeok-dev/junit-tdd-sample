package com.example.tdd1.domain;

import com.example.tdd1.dto.MockitoDto;

import java.util.List;

public interface MockitoRepository {
    public List<MockitoDto> getUsers(MockitoDto dto);
}
