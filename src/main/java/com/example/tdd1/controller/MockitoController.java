package com.example.tdd1.controller;

import com.example.tdd1.dto.MockitoDto;
import com.example.tdd1.service.MockBeanService;
import com.example.tdd1.service.mockito.MockitoService;
import com.example.tdd1.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MockitoController {

    private final MockitoService mockitoService;
    private final TestService testService;
    private final MockBeanService mockBeanService;

    public MockitoController(MockitoService mockitoService, TestService testService, MockBeanService mockBeanService) {
        log.info(">>>>>>>>>>>>>>>>> DI mockitoService: " + mockitoService);
        log.info(">>>>>>>>>>>>>>>>> DI testService: " + testService);
        log.info(">>>>>>>>>>>>>>>>> DI mockBeanService: " + mockBeanService);

        this.mockitoService = mockitoService;
        this.testService = testService;
        this.mockBeanService = mockBeanService;
    }

    @GetMapping("/example1")
    public Integer example1(Integer value1, Integer value2) {
        return this.mockitoService.calculateValue(value1, value2);
    }

    @GetMapping("/example2")
    public List<MockitoDto> example2(MockitoDto dto) {
        List<MockitoDto> resultList = this.mockitoService.getDataList(dto);
        log.info(">>>>>>>>>>> resultList.size: " + resultList.size());

        return resultList;
    }

    @GetMapping("/example3")
    public Integer example3(Integer value) {
        Integer resultValue = testService.multiply(value);
        log.info(">>>>>>>>>>> resultValue: " + resultValue);
        return resultValue;
    }

    @GetMapping("/example4")
    public Integer example4(Integer value)  {
        Integer resultValue = testService.division(value);
        log.info(">>>>>>>>>>> resultValue: " + resultValue);

        return resultValue;
    }

    @GetMapping("/example5")
    public Integer example5(Integer value) {
        Integer resultValue = testService.division(value);
        log.info(">>>>>>>>>>> resultValue: " + resultValue);

        testService.getMessage(value);

        return resultValue;
    }

    @GetMapping("/example6")
    public Integer example6(Integer ... values) {
        return testService.dynamicSum(values);
    }

    @GetMapping("/mockbeanTest")
    public List<String> mockbeanTest(String param) {
        log.info(">>>> mockbeanTest call!");
        return this.mockBeanService.getSamples(param);
    }
}
