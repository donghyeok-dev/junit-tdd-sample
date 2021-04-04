package com.example.tdd1.controller;

import com.example.tdd1.domain.User;
import com.example.tdd1.dto.MockitoDto;
import com.example.tdd1.dto.UserDto;
import com.example.tdd1.service.MockBeanService;
import com.example.tdd1.service.mockito.MockitoService;
import com.example.tdd1.service.TestService;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @GetMapping("/selectUser")
    public ResponseEntity<UserDto> selectUser(@RequestParam("userId") String userId) {
        UserDto user = mockitoService.selectUser(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/updateUser", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Boolean updateUser(@RequestBody UserDto user) {
        return mockitoService.updateUser(user);
    }

    @GetMapping("/spybeanTest")
    public String spybeanTest(HttpServletRequest request, String param) {
        log.info(String.format(">>>> spybeanTest call! request: %s param: %s  ", request, param));
        return this.mockitoService.getTeamName(param);
    }

    @GetMapping("/mockbeanTest")
    public List<String> mockbeanTest(HttpServletRequest request, String param) {
        log.info(String.format(">>>> mockbeanTest call! request: %s param: %s  ", request, param));
        return this.mockBeanService.getSamples(param);
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

}
