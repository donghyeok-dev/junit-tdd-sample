package com.example.tdd1.service;

import com.example.tdd1.domain.MockBeanRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MockBeanService {

//    private final MockBeanRepository mockBeanRepository;
//
//    public MockBeanService(MockBeanRepository mockBeanRepository) {
//        this.mockBeanRepository = mockBeanRepository;
//    }

    public List<String> getSamples(String param) {
        System.out.println(">>>> MockBeanService.getSamples method call!");
//        return this.mockBeanRepository.getSamples(param);
        return Collections.singletonList(String.format("Good %s", param));
    }

}
