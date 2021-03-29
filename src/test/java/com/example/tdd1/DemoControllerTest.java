package com.example.tdd1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
   *BDD 규칙으로 작성하기
   BDD(Behavior-Driven Development) 행위 주도 개발을 말하며 테스트 대상의 상태의 변화를 테스트하는 것이다.
   테스트 대상이 A 상태에서 출발하며(Given) 어떤 상태 변화를 가했을 때(When) 기대하는 상태로 완료되어야 한다(Then).

   //given

   //when

   //then


   *hamcrest는 JUnit에 사용되는 Matcher 라이브러리를 제공
      org.hamcrest.core: Object나 Value 값들에 대한 Matcher
      org.hamcrest.beans: Java Bean과 값 비교에 사용되는 Matcher
      org.hamcrest.collection : 배열과 컬렉션 관련 Matcher
      org.hamcrest.number
      org.hamcrest.object
      org.hamcrest.test 등이 있음.
   *JsonPath Expression : https://github.com/json-path/JsonPath
   * XPath
   *Restful Test Example : https://www.infoworld.com/article/3543268/junit-5-tutorial-part-2-unit-testing-spring-mvc-with-junit-5.html?page=3

*/
@SpringJUnitWebConfig
//@SpringBootTest
@Slf4j
class DemoControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DemoController()) //@SpringBootTest 또는 @SpringJUnitWebConfig Annotation을 사용해야 된다. (하나의 컨트롤을 대상으로 테스트)
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)    //@SpringBootTest Annotation을 사용해야 된다. (전체 컨트롤을 대상으로 테스트)
                .alwaysDo(log()) //org.springframework.test.web.servlet.result loging 범주 아래에 결과 데이터를 단일 DEBUG 메시지로 기록.
//                .addFilter(new CharacterEncodingFilter("UTF-8", true)) //모든 테스트들의 MocMvc Response ContentType에 charset=UTF-8가 추가된다.(response 받은 정보에 한글이 깨져서 추가함), 부가적으로 스프링 부트 2.2.0 MediaType.APPLICATION_JSON_UTF8가 depreacted됨.
//                .alwaysDo(print()) //모든 메서드에 결과 출력을 실행(변형 사용: print(System.err)를 호출하면 결과 데이터가 System.err로 출력되고 print(myWriter)를 호출하면 결과 데이터가 사용자 지정 writer로 출력)
//                .apply(sharedHttpSession()) //여러 요청에 걸쳐 HTTP 세션을 저장하고 재사용
                .build();
    }

    @Test
    @DisplayName("MvcResult 테스트")
    void testMvcResult() throws Exception {
        //given
        String testUserName = "감자칩";

        //when
        MvcResult result = this.mockMvc.perform(get("/simple")
                .param("name", testUserName)
                .contentType("text/plain;charset=UTF-8")
                .accept("text/plain;charset=UTF-8"))
                .andReturn();

        //then
        Assertions.assertEquals(result.getResponse().getContentAsString(StandardCharsets.UTF_8), testUserName);
    }

    @Test
    @DisplayName("비동기 테스트")
    void testAsync() throws Exception {
        //given
        String testUserName = "브래드";

        //when
        MvcResult mvcResult = this.mockMvc.perform(get("/testAsync")
                .param("name", testUserName)
                .contentType("text/plain;charset=UTF-8")
                .accept("text/plain;charset=UTF-8"))

                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(testUserName))
                .andReturn();

        //then
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string(testUserName));
    }

    @Test
    @DisplayName("DTO를 Json String으로 변환하여 Post 요청")
    void requestPostDto() throws Exception {
        //given
        DemoDto dto = DemoDto.builder()
                .userId("test")
                .userPassword("1234")
                .userName("study")
                .age(30)
                .build();

        //when
        ResultActions resultActions = this.mockMvc.perform(post("/requestPostDto")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        /*
        [
            {"userId":"test","userPassword":"1234","userName":"파","age":0},
            {"userId":"test","userPassword":"1234","userName":"마늘","age":0},
            {"userId":"test","userPassword":"1234","userName":"양파","age":0}]
        */
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(header().string(HttpHeaders.LOCATION, "/requestPostDto"))
                .andExpect(matchAll(
                        jsonPath("$.[*].userName").isNotEmpty(),
                        jsonPath("$.[*].userPassword").isNotEmpty()
                ))
                .andExpect(
                        jsonPath(".[*].userName",
                                Matchers.everyItem(
                                        Matchers.anyOf(  /* anyOf, allOf, both, either ... */
                                                Matchers.is(notNullValue()),
                                                Matchers.is("양파"),
                                                Matchers.hasItem("파"),
                                                Matchers.containsString("마늘"),
                                                Matchers.not("감자")
                                                /* matchesRegex, startsWith, startsWithIgnoringCase, endsWith ... */
                                        )
                                )
                        )
                );

    }

    @Test
    @DisplayName("String으로 Post 요청")
    void requestPostString() throws Exception {
        //when
        ResultActions resultActions = this.mockMvc.perform(post("/requestPostString")
                .param("name", "donghyeok")
                .param("country", "대한민국")
                .contentType("text/plain;charset=UTF-8")
                .accept("text/plain;charset=UTF-8") //accept는 client가 server에게 어떤형식(MediaType)으로 응답을 달라고 요청하는 것. 이 라인을 주석하면 Content type = text/plain;charset=ISO-8859-1로 오면서 한글이 깨진다.
        );

        //then
        resultActions.andExpect(status().isOk());
    }


    @Test
    @DisplayName("DTO를 MultiValueMap 타입으로 변환하여 GET 요청")
    void requestGetDto() throws Exception {
        //given
        DemoDto dto = DemoDto.builder()
                .userId("test")
                .userPassword("1234")
                .userName("study")
                .age(30)
                .build();

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/requestGetDto")
                .params(convertDtoToMultiValueMap(objectMapper, dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());

    }

    @Test
    @DisplayName("Multipart 요청")
    void requestMultipart() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        //when
        ResultActions resultActions = this.mockMvc.perform(multipart("/requestMultipart").file(file));

        //then
        resultActions.andExpect(status().isOk());
    }

    //아래 함수 출처: https://jojoldu.tistory.com/478?category=635883
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
