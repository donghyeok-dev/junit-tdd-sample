package com.example.tdd1;

import java.util.List;

public interface MockitoService {
    public List<MockitoDto> getDataList(MockitoDto dto);
    public int calculateValue(int a, int b);
}
