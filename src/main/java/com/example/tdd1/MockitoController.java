package com.example.tdd1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MockitoController {

    private final MockitoService mockitoService;
    private final TestService testService;

    public MockitoController(MockitoService mockitoService, TestService testService) {
        log.info(">>>>>>>>>>>>>>>>> DI mockitoService: " + mockitoService);
        log.info(">>>>>>>>>>>>>>>>> DI testService: " + testService);
        this.mockitoService = mockitoService;
        this.testService = testService;
    }

    @GetMapping("/example1")
    public Integer example1(Integer value1, Integer value2) {
        return this.mockitoService.calculateValue(value1, value2);
    }

    @GetMapping("/example2")
    public List<MockitoDto> example2(MockitoDto dto) {
        log.info(">>>>>>>>>>>>>>>>>>>> example2 getDataList called! dto: " + dto);
        List<MockitoDto> resultList = this.mockitoService.getDataList(dto);
        log.info(">>>>>>>>>>> resultList.size: " + resultList.size());

        return resultList;
    }

    @GetMapping("/example3")
    public Integer example3(Integer value) {
        Integer resultValue = testService.calculateValue(value);
        log.info(">>>>>>>>>>> resultValue: " + resultValue);
        return resultValue;
    }
}
