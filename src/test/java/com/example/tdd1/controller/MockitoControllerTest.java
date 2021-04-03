package com.example.tdd1.controller;


import com.example.tdd1.dto.MockitoDto;
import com.example.tdd1.service.MockBeanService;
import com.example.tdd1.service.mockito.MockitoService;
import com.example.tdd1.service.TestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tdd1.config.TestAdvice;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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
//@SpringBootTest
//@WebMvcTest
//@MockitoSettings(strictness = Strictness.WARN)
//@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class MockitoControllerTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none(); //앤 멀해주는걸까?

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Mock
    MockitoService mockitoService;

    @Spy
    TestService testService;

    @MockBean
    MockBeanService mockBeanService;

    @InjectMocks
    MockitoController mockitoController;

    @Captor
    ArgumentCaptor<MockitoDto> captor;

    @BeforeEach
    void setUp(WebApplicationContext wac) {

        this.objectMapper = new ObjectMapper();
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockitoController)
                .setHandlerExceptionResolvers(withExceptionControllerAdvice())
                .alwaysDo(log())
                .build();
    }

    @Test
    @DisplayName("MockBean 테스트")
    void testMockbean() throws Exception {
        final String param = "Nice";

        when(this.mockBeanService.getSamples(anyString())).thenReturn(Arrays.asList("안녕", "만나서", "반가워"));

//        System.out.println(">>> this.mockBeanService.getSamples: " + this.mockBeanService.getSamples(param));
        this.mockMvc.perform(get("/mockbeanTest").param("param", param));

        verify(this.mockBeanService).getSamples(param);
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
    @DisplayName("기본 테스트")
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
    @DisplayName("상세 테스트")
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
        resultActions.andExpect(status().isOk()); //http 상태코드 200(정상)인지 검사.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.everyItem(Matchers.notNullValue()))); //리턴 받은 json객체의 name필드 중 null값이 포함되어있는지 검사.
        resultActions.andExpect(jsonPath("$.[*].name", Matchers.hasSize(3)));
//        ArgumentCaptor<MockitoDto> captor = ArgumentCaptor.forClass(MockitoDto.class);
        verify(this.mockitoService).getDataList(captor.capture()); //호출된 메소드에 전달된 값 검증하기 (메소드 1번만 호출 허용)
        assertEquals(dto, captor.getValue());

        /*
            public static <T> T verify(T mock, VerificationMode mode)
            public interface VerificationMode
            public class VerificationModeFactory
            public class Times implements VerificationInOrderMode, VerificationMode
         */
        verify(this.mockitoService).getDataList(any()); // 1번만 호출되었는지 검사
        verify(this.mockitoService, times(1)).getDataList(isA(MockitoDto.class)); // 지정된 호출횟수 만큼 호출되었는지 검사
//        verify(this.mockitoService, timeout(1)).getDataList(isA(MockitoDto.class)); //비동기 코드를 테스트 시 지정된 시간 내에 메소드가 처리되는지
    }

    @Test
    @DisplayName("when doReturn 차이 테스트")
    void testWhenDoReturn() throws Exception {
        final int value = 3;

        //5번호출됨 (리턴값 1번 perform:10, 2번 perform: 10,  3번 perform: 10)
//        when(this.testService.multiply(3)).thenReturn(10);
//        when(this.testService.multiply(4)).thenReturn(11);
//        when(this.testService.multiply(5)).thenReturn(12);
//        when(this.testService.multiply(6)).thenReturn(13);
//        when(this.testService.multiply(7)).thenReturn(14);

        //0번호출 (리턴값 1번 perform:10, 2번 perform: 10,  3번 perform: 10)
//        doReturn(10).when(this.testService).multiply(3);
//        doReturn(11).when(this.testService).multiply(4);
//        doReturn(12).when(this.testService).multiply(5);
//        doReturn(13).when(this.testService).multiply(6);
//        doReturn(14).when(this.testService).multiply(7);

        //1번만 호출됨 (리턴값 1번 perform:14, 2번 perform: 14,  3번 perform: 14)
//        when(this.testService.multiply(3)).thenReturn(10);
//        when(this.testService.multiply(3)).thenReturn(11);
//        when(this.testService.multiply(3)).thenReturn(12);
//        when(this.testService.multiply(3)).thenReturn(13);
//        when(this.testService.multiply(3)).thenReturn(14);

        //0번 호출 (리턴값 1번 perform:14, 2번 perform: 14,  3번 perform: 14)
//        doReturn(10).when(this.testService).multiply(3);
//        doReturn(11).when(this.testService).multiply(3);
//        doReturn(12).when(this.testService).multiply(3);
//        doReturn(13).when(this.testService).multiply(3);
//        doReturn(14).when(this.testService).multiply(3);

        //1번만 호출됨 (리턴값 1번 perform:10, 2번 perform: 11,  3번 perform: 12)
//        when(this.testService.multiply(3)).thenReturn(10, 11, 12, 13, 14);

        //0번 호출 (리턴값 1번 perform:10, 2번 perform: 11,  3번 perform: 12)
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
    @DisplayName("Exception 테스트")
    void testException() {
        //value가 0으로 넘어가면 실제 메서드에서 ArithmeticException: / by zero 발생.
        this.mockMvc.perform(get("/example4")
                .param("value", String.valueOf(0)))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ArithmeticException))
                .andReturn();

        //파라메터 10이 넘어오면 RuntimeException 발생하도록 설정함.
        doThrow(new RuntimeException("아메리카노")).when(this.testService).division(10);

        //value 10을 넘겨서 RuntimeException 발생 확인.
        this.mockMvc.perform(get("/example4")
                .param("value", String.valueOf(10)))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof RuntimeException)) //ArithmeticException
                .andReturn();

        verify(this.testService, times(2)).division(isA(Integer.class));
    }

    @Test
    @DisplayName("doNothing 테스트")
    void testDoNothing() throws Exception {
        //testService의 getMessage 메서드 호출 무시.
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);

        //getMessage 메서드는 호출 하진 않지만  넘어가는 파라메터를 캡처함.
        doNothing().when(this.testService).getMessage(valueCapture.capture());

        this.mockMvc.perform(get("/example5")
                .param("value", "10"))
                .andReturn();

        //캡처한 파라메터값 검증.
        assertEquals(10, valueCapture.getValue());

        verify(this.testService).division(anyInt());
        verify(this.testService).getMessage(anyInt());
    }

    @Test
    @DisplayName("Answer 테스트")
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
    @DisplayName("doCallRealMethod 테스트")
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
            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", dto, e);
            throw new IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.");
        }
    }

}