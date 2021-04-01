package com.example.tdd1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
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
//    public ExpectedException thrown = ExpectedException.none();

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
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
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
    @DisplayName("mockito 기본 테스트")
    void test1() throws Exception {
        //given
        final Integer value1 = 3;
        final Integer value2 = 5;

        when(this.mockitoService.calculateValue(value1, value2)).thenReturn(value1 * value2);

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

        when(this.mockitoService.getDataList(any())).thenReturn(resultList);
//        Mockito.when(this.mockitoService.getDataList(Mockito.isA(MockitoDto.class))).then(invocation -> {
//            log.info("answer call!");
//            return resultList;
//        });

//        this.mockitoService.getDataList(any());

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
        Mockito.verify(this.mockitoService).getDataList(captor.capture()); //호출된 메소드에 전달된 값 검증하기 (메소드 1번만 호출 허용)
        Assertions.assertEquals(dto, captor.getValue());

        /*
            public static <T> T verify(T mock, VerificationMode mode)
            public interface VerificationMode
            public class VerificationModeFactory
            public class Times implements VerificationInOrderMode, VerificationMode
         */
        Mockito.verify(this.mockitoService).getDataList(any()); // 1번만 호출되었는지 검사
        Mockito.verify(this.mockitoService, Mockito.times(1)).getDataList(Mockito.isA(MockitoDto.class)); // 지정된 호출횟수 만큼 호출되었는지 검사
//        Mockito.verify(this.mockitoService, Mockito.timeout(1)).getDataList(Mockito.isA(MockitoDto.class)); //비동기 코드를 테스트 시 지정된 시간 내에 메소드가 처리되는지
    }

    @Test
    @DisplayName("when doReturn 차이 테스트")
    void test3() throws Exception {

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
        doReturn(10, 11, 12, 13, 14).when(this.testService).multiply(3);


        this.mockMvc.perform(get("/example3")
                .param("value", String.valueOf(value))).andExpect(status().isOk());

        this.mockMvc.perform(get("/example3")
                .param("value", String.valueOf(value))).andExpect(status().isOk());

        this.mockMvc.perform(get("/example3")
                .param("value", String.valueOf(value))).andExpect(status().isOk());

        Mockito.verify(this.testService, Mockito.times(3)).multiply(Mockito.isA(Integer.class));
    }


    @SneakyThrows
    @Test
    @DisplayName("Mockito Exception 테스트")
    void test4()  {
        //doThrow(new RuntimeException("aaaa")).when(this.testService).division(10);
        //@Rule public ExpectedException expectedException = ExpectedException.none(); 앤 멀해주는걸까?

        this.mockMvc.perform(get("/example4")
                .param("value", String.valueOf(0)))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ArithmeticException))
                .andReturn();

        Mockito.verify(this.testService, Mockito.times(1)).division(Mockito.isA(Integer.class));
    }

    @Test
    @DisplayName("doXXX Method 테스트")
    void test5() {
        //https://stacktraceguru.com/unittest/mock-void-method#donothing_voidMethod

        //메서드 호출을 무시한다.(내 추측인데 특정 메서드를 호출할때 그 내부에 포함된 메서드들 중 하나 이상을 무시하고 싶을때 사용하는 목적인 것 같다.)
        /*
        doNothing().when(mockedUserRepository).updateName(anyLong(),anyString());  // doNothintg()이 포함된 결과와 포함되지 않는 결과를 비교해보자.
        userService.updateName(1L,"void mock test");
        verify(mockedUserRepository, times(1)).updateName(1L,"void mock test");

        //메서드 호출은 무시되지만 해당 메서드에 전달된 파라메터는 검사해볼수 있다. (오~~~)
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> nameCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockedUserRepository).updateName(idCapture.capture(),nameCapture.capture());

        userService.updateName(1L,"void mock test");

        assertEquals(1L, idCapture.getValue());
        assertEquals("void mock test", nameCapture.getValue());
        */





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