package com.example.tdd1.basic;


import com.example.tdd1.basic.config.TestAdvice;
import com.example.tdd1.basic.MockitoController;
import com.example.tdd1.basic.dto.MockitoDto;
import com.example.tdd1.basic.service.TestService;
import com.example.tdd1.basic.service.mockito.MockitoService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@Slf4j
class MyStandaloneSetupTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none(); //??? ???????????????????

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Mock
    MockitoService mockitoService;

    @Spy
    TestService testService;

    @InjectMocks
    MockitoController mockitoController;

    @Captor
    ArgumentCaptor<MockitoDto> captor;

    @BeforeEach
    void setUp(WebApplicationContext wac) {

        this.objectMapper = new ObjectMapper();
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockitoController)
                .setHandlerExceptionResolvers(withExceptionControllerAdvice())
                .alwaysDo(log())
                .build();
    }

    private ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
        final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod, final Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(TestAdvice.class).resolveMethod(exception);
                if (method != null) {
                    return new ServletInvocableHandlerMethod(new TestAdvice(), method);
                }
                return super.getExceptionHandlerMethod(handlerMethod, exception);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
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
//        when(this.mockitoService.getDataList(isA(MockitoDto.class))).then(invocation -> {
//            log.info("answer call!");
//            return resultList;
//        });

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/example2")
                .params(convertDtoToMultiValueMap(objectMapper, dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk()); //http ???????????? 200(??????)?????? ??????.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.everyItem(Matchers.notNullValue()))); //?????? ?????? json????????? name?????? ??? null?????? ????????????????????? ??????.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.hasSize(3)));
//        ArgumentCaptor<MockitoDto> captor = ArgumentCaptor.forClass(MockitoDto.class);
        verify(this.mockitoService).getDataList(captor.capture()); //????????? ???????????? ????????? ??? ???????????? (????????? 1?????? ?????? ??????)
        assertEquals(dto, captor.getValue());


//            public static <T> T verify(T mock, VerificationMode mode)
//            public interface VerificationMode
//            public class VerificationModeFactory
//            public class Times implements VerificationInOrderMode, VerificationMode

        verify(this.mockitoService).getDataList(any()); // 1?????? ?????????????????? ??????
        verify(this.mockitoService, times(1)).getDataList(isA(MockitoDto.class)); // ????????? ???????????? ?????? ?????????????????? ??????
//        verify(this.mockitoService, timeout(1)).getDataList(isA(MockitoDto.class)); //????????? ????????? ????????? ??? ????????? ?????? ?????? ???????????? ???????????????
    }

    @Test
    @DisplayName("when doReturn ?????? ?????????")
    void testWhenDoReturn() throws Exception {
        final int value = 3;

        //5???????????? (????????? 1??? perform:10, 2??? perform: 10,  3??? perform: 10)
//        when(this.testService.multiply(3)).thenReturn(10);
//        when(this.testService.multiply(4)).thenReturn(11);
//        when(this.testService.multiply(5)).thenReturn(12);
//        when(this.testService.multiply(6)).thenReturn(13);
//        when(this.testService.multiply(7)).thenReturn(14);

        //0????????? (????????? 1??? perform:10, 2??? perform: 10,  3??? perform: 10)
//        doReturn(10).when(this.testService).multiply(3);
//        doReturn(11).when(this.testService).multiply(4);
//        doReturn(12).when(this.testService).multiply(5);
//        doReturn(13).when(this.testService).multiply(6);
//        doReturn(14).when(this.testService).multiply(7);

        //1?????? ????????? (????????? 1??? perform:14, 2??? perform: 14,  3??? perform: 14)
//        when(this.testService.multiply(3)).thenReturn(10);
//        when(this.testService.multiply(3)).thenReturn(11);
//        when(this.testService.multiply(3)).thenReturn(12);
//        when(this.testService.multiply(3)).thenReturn(13);
//        when(this.testService.multiply(3)).thenReturn(14);

        //0??? ?????? (????????? 1??? perform:14, 2??? perform: 14,  3??? perform: 14)
//        doReturn(10).when(this.testService).multiply(3);
//        doReturn(11).when(this.testService).multiply(3);
//        doReturn(12).when(this.testService).multiply(3);
//        doReturn(13).when(this.testService).multiply(3);
//        doReturn(14).when(this.testService).multiply(3);

        //1?????? ????????? (????????? 1??? perform:10, 2??? perform: 11,  3??? perform: 12)
//        when(this.testService.multiply(3)).thenReturn(10, 11, 12, 13, 14);

        //0??? ?????? (????????? 1??? perform:10, 2??? perform: 11,  3??? perform: 12)
//        doReturn(10, 11, 12, 13, 14).when(this.testService).multiply(3);

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

    @Test
    @DisplayName("InOrder ?????????")
    void testInOrder() {
        this.testService.multiply(3);
        this.testService.multiply(4);
        this.testService.multiply(5);

        //???????????? ?????? ????????? ??????
        InOrder inOrder =  inOrder(this.testService);
        inOrder.verify(this.testService).multiply(3);
        inOrder.verify(this.testService).multiply(4);
        inOrder.verify(this.testService).multiply(5);
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