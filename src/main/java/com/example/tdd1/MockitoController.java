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

    public MockitoController(MockitoService mockitoService) {
        log.info(">>>>>>>>>>>>>>>>> DI mockitoService: " + mockitoService);
        this.mockitoService = mockitoService;
    }

    @GetMapping("/example1")
    public Integer example1(Integer value1, Integer value2) {
        return this.mockitoService.calculateValue(value1, value2);
    }

    @GetMapping("/example2")
    public List<MockitoDto> example2(MockitoDto dto) {
        log.info(">>>>>>>>>>>>>>>>>>>> example2 getDataList called! dto: " + dto);
        return this.mockitoService.getDataList(dto);
    }
}
