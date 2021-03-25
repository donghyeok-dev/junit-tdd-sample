package com.example.tdd1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class DemoControllerTest {

    MockMvc mockMvc;


    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

      /*  mockMvc = standaloneSetup(new AccountController())
                .defaultRequest(get("/")
                        .contextPath("/app").servletPath("/main")
                        .accept(MediaType.APPLICATION_JSON)).build();*/
    }

    @DisplayName("1")
    @Test
    void name() throws Exception {
        mockMvc = standaloneSetup(new DemoController())
                .defaultRequest(get("/checkName").accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoController())
                .apply(sharedHttpSession())
                .build();

        // file upload requests
        mockMvc.perform(multipart("/checkName").file("a1", "ABC".getBytes(StandardCharsets.UTF_8)));

        //URI template style
        //URI 템플릿과 함께 제공된 쿼리 파라미터는 디코딩
        mockMvc.perform(get("/checkName?name={myName}", "SuperMan"));

        //Servlet request parameters
        //요청 파라미터는 이미 디코딩된 것으로 예상된다는 점에 유의
        mockMvc.perform(get("/hotels").param("name", "Jungle"));

        mockMvc.perform(get("/app/main/hotels").contextPath("/app").servletPath("/main"));


        Assertions.assertEquals(1, 1);

    }
}