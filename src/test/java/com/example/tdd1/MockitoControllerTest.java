package com.example.tdd1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
//@SpringBootTest
//@WebMvcTest
@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class MockitoControllerTest { /*extends BaseTest{*/
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Mock
    MockitoService mockitoService;

//    @InjectMocks
//    MockitoController mockitoController;

    @Captor
    ArgumentCaptor<MockitoDto> captor;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.objectMapper = new ObjectMapper();
        this.mockitoService = mock(MockitoService.class);
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
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
    @DisplayName("Mockito 테스트 2")
    void test2() throws Exception {
        //given
        final MockitoDto dto = MockitoDto.builder()
                .value1(2)
                .value2(3)
                .name("spring")
                .build();

        List<MockitoDto> resultList = Arrays.asList(dto, dto, dto);

//        Mockito.when(this.mockitoRepository.getDataList(Mockito.isA(MockitoDto.class))).thenReturn(resultList);
//        Assertions.assertEquals(this.mockitoRepository.getDataList(dto), resultList);

        Mockito.when(this.mockitoService.getDataList(any())).thenReturn(resultList);
//        Mockito.when(this.mockitoService.getDataList(Mockito.isA(MockitoDto.class))).then(invocation -> {
//            log.info("answer call!");
//            return resultList;
//        });

        this.mockitoService.getDataList(any());

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/example2")
                .params(convertDtoToMultiValueMap(objectMapper, dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk()); //http 상태코드 200(정상)인지 검사.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.everyItem(Matchers.notNullValue()))); //리턴 받은 json객체의 name필드 중 null값이 포함되어있는지 검사.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.hasSize(3)));
        //ArgumentCaptor<MockitoDto> captor = ArgumentCaptor.forClass(MockitoDto.class);
//        Mockito.verify(this.mockitoService).getDataList(captor.capture()); //호출된 메소드에 전달된 값 검증하기 (메소드 1번만 호출 허용)
//        Assertions.assertEquals(dto, captor.getValue());

        /*
            public static <T> T verify(T mock, VerificationMode mode)
            public interface VerificationMode
            public class VerificationModeFactory
            public class Times implements VerificationInOrderMode, VerificationMode
         */
//        Mockito.verify(this.mockitoService).getDataList(any()); // 1번만 호출되었는지 검사
//        Mockito.verify(this.mockitoService, Mockito.times(1)).getDataList(Mockito.isA(MockitoDto.class)); // 지정된 호출횟수 만큼 호출되었는지 검사
//        Mockito.verify(this.mockitoService, Mockito.timeout(1)).getDataList(Mockito.isA(MockitoDto.class)); //비동기 코드를 테스트 시 지정된 시간 내에 메소드가 처리되는지
    }

    MultiValueMap<String, String> convertDtoToMultiValueMap(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
            }));
            return params;
        } catch (Exception e) {
            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", dto, e);
            throw new IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.");
        }
    }

}