package com.example.tdd1.basic;


import com.example.tdd1.basic.dto.MockitoDto;
import com.example.tdd1.basic.service.MockBeanService;
import com.example.tdd1.basic.service.mockito.MockitoService;
import com.example.tdd1.basic.service.TestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Slf4j
class MyWebMvcTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @MockBean
    MockitoService mockitoService;

    @SpyBean
    TestService testService;

    @MockBean
    MockBeanService mockBeanService;

    @Captor
    ArgumentCaptor<MockitoDto> captor;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("MockBean ?????????")
    void testMockbean() throws Exception {
        final String param = "Nice";

        when(this.mockBeanService.getSamples(anyString())).thenReturn(Arrays.asList("??????", "?????????", "?????????"));

        System.out.println(">>>>>>>>> " + this.mockBeanService.getSamples(param));

        MvcResult mvcResult = this.mockMvc.perform(get("/mockbeanTest")
                .param("param", param))
                .andExpect(status().isOk()).andReturn();

        System.out.println(">> mvcResult: " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

    }

    @Test
    @DisplayName("?????? ?????????")
    void testBasic() throws Exception {
        //given
        final Integer value1 = 3;
        final Integer value2 = 5;

        when(this.mockitoService.calculateValue(value1, value2)).thenReturn(value1 * value2);

        //when
        MvcResult mvcResult = this.mockMvc.perform(get("/example1")
                .param("value1", String.valueOf(value1))
                .param("value2", String.valueOf(value2))).andReturn();

        //then
        verify(this.mockitoService).calculateValue(value1, value2);
        assertEquals(mvcResult.getResponse().getContentAsString(), String.valueOf(value1 * value2));
    }

    @Test
    @DisplayName("?????? ?????????")
    void testDetail() throws Exception {
        //given
        final MockitoDto dto = MockitoDto.builder()
                .value1(2)
                .value2(3)
                .name("spring")
                .build();

        List<MockitoDto> resultList = Arrays.asList(dto, dto, dto);

        when(this.mockitoService.getDataList(any())).thenReturn(resultList);

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/example2")
                .params(convertDtoToMultiValueMap(objectMapper, dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk()); //http ???????????? 200(??????)?????? ??????.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.everyItem(Matchers.notNullValue()))); //?????? ?????? json????????? name?????? ??? null?????? ????????????????????? ??????.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.hasSize(3)));

        verify(this.mockitoService).getDataList(captor.capture()); //????????? ???????????? ????????? ??? ???????????? (????????? 1?????? ?????? ??????)
        assertEquals(dto, captor.getValue());

        verify(this.mockitoService).getDataList(any()); // 1?????? ?????????????????? ??????
        verify(this.mockitoService, times(1)).getDataList(isA(MockitoDto.class)); // ????????? ???????????? ?????? ?????????????????? ??????
    }

    @Test
    @DisplayName("when doReturn ?????? ?????????")
    void testWhenDoReturn() throws Exception {
        final int value = 3;

        this.mockMvc.perform(get("/example3")
                .param("value", String.valueOf(value))).andExpect(status().isOk());

        this.mockMvc.perform(get("/example3")
                .param("value", String.valueOf(value))).andExpect(status().isOk());

        this.mockMvc.perform(get("/example3")
                .param("value", String.valueOf(value))).andExpect(status().isOk());

//        verify(this.testService, times(3)).multiply(isA(Integer.class));
    }


    @SneakyThrows
    @Test
    @DisplayName("Exception ?????????")
    void testException() {
        //value??? 0?????? ???????????? ?????? ??????????????? ArithmeticException: / by zero ??????.
        this.mockMvc.perform(get("/example4")
                .param("value", String.valueOf(0)))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ArithmeticException))
                .andReturn();

        //???????????? 10??? ???????????? RuntimeException ??????????????? ?????????.
        doThrow(new RuntimeException("???????????????")).when(this.testService).division(10);

        //value 10??? ????????? RuntimeException ?????? ??????.
        this.mockMvc.perform(get("/example4")
                .param("value", String.valueOf(10)))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof RuntimeException)) //ArithmeticException
                .andReturn();

        verify(this.testService, times(2)).division(isA(Integer.class));
    }

    @Test
    @DisplayName("doNothing ?????????")
    void testDoNothing() throws Exception {
        //testService??? getMessage ????????? ?????? ??????.
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);

        //getMessage ???????????? ?????? ?????? ?????????  ???????????? ??????????????? ?????????.
        doNothing().when(this.testService).getMessage(valueCapture.capture());

        this.mockMvc.perform(get("/example5")
                .param("value", "10"))
                .andReturn();

        //????????? ??????????????? ??????.
        assertEquals(10, valueCapture.getValue());

        verify(this.testService).division(anyInt());
        verify(this.testService).getMessage(anyInt());
    }

    @Test
    @DisplayName("Answer ?????????")
    void testAnswer() {
        Answer<Integer> answer = new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return Arrays.stream(invocation.getArguments())
                        .map(o -> Integer.parseInt(o.toString()))
                        .reduce(0, Integer::sum);
            }
        };

        when(this.testService.dynamicSum(10, 20, 30)).thenAnswer(answer);
        System.out.println(">>> result: " + this.testService.dynamicSum(10, 20, 30));

        doAnswer(answer).when(this.testService).dynamicSum(10, 20, 30);
        System.out.println(">>> result: " + this.testService.dynamicSum(10, 20, 30));

        verify(this.testService, times(2)).dynamicSum(any());
    }

    @Test
    @DisplayName("doCallRealMethod ?????????")
    void testDoCallRealMethod() {
        //doCallRealMethod().when(this.testService).getTeamName(anyString());

        when(this.testService.getTeamName(anyString())).thenReturn("aaaa");

        this.testService.getTeamName("Spring");

        verify(this.testService, times(1)).getTeamName("Spring");
    }

    MultiValueMap<String, String> convertDtoToMultiValueMap(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
            }));
            return params;
        } catch (Exception e) {
            log.error("Url Parameter ????????? ????????? ??????????????????. requestDto={}", dto, e);
            throw new IllegalStateException("Url Parameter ????????? ????????? ??????????????????.");
        }
    }

}