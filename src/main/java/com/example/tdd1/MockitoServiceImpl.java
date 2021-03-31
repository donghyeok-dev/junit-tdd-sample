package com.example.tdd1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MockitoServiceImpl implements MockitoService {

//    private final MockitoRepository mockitoRepository;
//
//    public MockitoServiceImpl(MockitoRepository mockitoRepository) {
//        this.mockitoRepository = mockitoRepository;
//    }

    @Override
    public List<MockitoDto> getDataList(MockitoDto dto) {
        log.info("============= MockitoServiceImpl getDataList called!");
        return Collections.emptyList();
//        return mockitoRepository.getDataList(dto);
    }

    @Override
    public int calculateValue(int a, int b) {
        return 0;
    }
}
