package com.example.tdd1;

import java.util.List;

public class MockitoServiceImpl implements MockitoService{

    private final MockitoRepository mockitoRepository;

    public MockitoServiceImpl(MockitoRepository mockitoRepository) {
        this.mockitoRepository = mockitoRepository;
    }

    @Override
    public List<MockitoDto> getDataList(MockitoDto dto) {
        return mockitoRepository.getDataList(dto);
    }

    @Override
    public int calculateValue(int a, int b) {
        return 0;
    }
}
