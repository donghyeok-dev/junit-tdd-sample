package com.example.tdd1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;




import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig
//@SpringBootTest
@Slf4j
class DemoControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DemoController()) //@SpringBootTest 또는 @SpringJUnitWebConfig Annotation을 사용해야 된다.
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)    //@SpringBootTest Annotation을 사용해야 된다.
                //.defaultRequest(get("/checkName2")) //기본 요청 경로
                //.alwaysExpect(status().isOk()) //모든 테스트 응답 결과가 200인지
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) //모든 테스트들의 MocMvc Response ContentType에 charset=UTF-8가 추가된다.(response 받은 정보에 한글이 깨져서 추가함), 부가적으로 스프링 부트 2.2.0 MediaType.APPLICATION_JSON_UTF8가 depreacted됨.
//                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
//                .alwaysExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .build();
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void name() throws Exception {
//        this.mockMvc.perform(multipart("/checkName").file("a1", "ABC".getBytes(StandardCharsets.UTF_8)));
//        this.mockMvc.perform(get("/checkName?name={1}{2}{custom}{myself}{aaaaaaaa}", "kdh", "_A", "_B", "_C", "_D")); //name: kdh_A_B_C
//        this.mockMvc.perform(get("/checkName").param("name", "kdh"));

        DemoDto dto = DemoDto.builder()
                .userId("test")
                .userPassword("1234")
                .userName("김동혁")
                .age(39)
                .build();

        log.info("TEST>> objectMapper.writeValueAsString(dto): " + objectMapper.writeValueAsString(dto));

        //
        this.mockMvc.perform(post("/postRequestObject")
//                            .params(convertDto(objectMapper, dto))        //get 방식용
                            .content(objectMapper.writeValueAsString(dto))  //post 용 ( writeValueAsString는 {"userId":"test","userPassword":"1234","userName":"김동혁"} 형식의 JSON String으로 변환해준다.)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8") )
                    .andExpect(status().isOk())
                    .andDo(print());

        this.mockMvc.perform(post("/postRequestQueryString")
                        .param("name", "동혁")
                        .param("country", "대한민국")
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN)
                        .characterEncoding("UTF-8")
//                        .accept(MediaType.APPLICATION_JSON) //accept는 client가 server에게 어떤형식(MediaType)으로 응답을 달라고 요청하는 것.
                )

                .andExpect(status().isOk())
                .andDo(print());
    }

    public  MultiValueMap<String, String> convertDto(ObjectMapper objectMapper, Object dto) { // (2)
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {}); // (3)
            params.setAll(map); // (4)
            return params;
        } catch (Exception e) {
            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", dto, e);
            throw new IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.");
        }
    }
}
