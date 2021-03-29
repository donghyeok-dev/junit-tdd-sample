package com.example.tdd1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class MockitoControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    private AutoCloseable closeable;

    @Mock
    MockitoService mockitoService;

    @BeforeAll
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        //this.mockitoService = mock(MockitoService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MockitoController(mockitoService))
                .alwaysDo(log())
                .build();
    }

    @Test
    @DisplayName("mockito 기본 테스트")
    void test1() throws Exception {
        //given
        final int value1 = 3;
        final int value2 = 5;

        Mockito.when(this.mockitoService.calculateValue(value1, value2)).thenReturn(value1 * value2);

        //when
        MvcResult mvcResult = this.mockMvc.perform(get("/example1")
                .param("value1", String.valueOf(value1))
                .param("value2", String.valueOf(value2))).andReturn();

        //then
        Mockito.verify(this.mockitoService).calculateValue(value1, value2);
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), String.valueOf(value1 * value2));
    }

    @Test
    @DisplayName("Mockito Answer 테스트")
    void test2() throws Exception {
        //given
        final int value1 = 3;
        final int value2 = 5;

        MockitoDto dto = MockitoDto.builder()
                .value1(value1)
                .value2(value2)
                .name("spring")
                .build();

        Mockito.when(this.mockitoService.getDataList(dto)).then(invocation -> {
            log.info("Mockito answer test!");
            return Collections.emptyList();
        });

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/example2")
                .params(convertDtoToMultiValueMap(objectMapper, dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        Mockito.verify(this.mockitoService, Mockito.times(1)).getDataList(dto);
    }

    @AfterAll
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    MultiValueMap<String, String> convertDtoToMultiValueMap(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {}));
            return params;
        } catch (Exception e) {
            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", dto, e);
            throw new IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.");
        }
    }

}