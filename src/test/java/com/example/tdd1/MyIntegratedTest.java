package com.example.tdd1;


import com.example.tdd1.dto.UserDto;
import com.example.tdd1.service.MockBeanService;
import com.example.tdd1.service.mockito.MockitoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
//@AutoConfigureMockMvc
class MyIntegratedTest {

    @LocalServerPort
    int randomServerPort;

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    MockBeanService mockBeanService;

    @SpyBean
    MockitoService mockitoService;

//    @Test
//    @DisplayName("통합테스트 MockMvc")
//    void testMockMvc() throws Exception {
//        //@SpringBootTest와 @AutoConfigureMoc
//        kMvc가 Annotated되어야 한다.
//        String param = "Hello";
//
//        when(this.mockBeanService.getSamples(anyString())).thenReturn(Arrays.asList("안녕", "만나서", "반가워"));
//
//        MvcResult mvcResult = this.mockMvc.perform(get("/mockbeanTest")
//                .param("param", param))
//                .andExpect(status().isOk()).andReturn();
//
//        System.out.println(">> mvcResult: " + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
//    }

    @Test
    @DisplayName("통합테스트 testRestTemplate")
    void testRestTemplate() {
        String param = "Hello";

        when(mockBeanService.getSamples(param)).thenReturn(Arrays.asList("안녕"));

         ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/mockbeanTest?param={param}", String.class, param);

         assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("통합테스트 SpyBean")
    void testSpyBean() {
        String param = "Hello";

        when(mockitoService.getTeamName(anyString())).thenReturn("Test Team");

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/spybeanTest?param={param}", String.class, param);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("통합테스트 select query")
    void testSelectQuery() {
        String userId = "test1";

        //when(mockitoService.getTeamName(anyString())).thenReturn("Test Team");

        ResponseEntity<Object> responseEntity = testRestTemplate.getForEntity("/selectUser?userId={userId}", Object.class, userId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("통합테스트 update query")
    void testUpdateQuery()  {
        UserDto user = UserDto.builder().userId("test1").userName("김태희").build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<Boolean> responseEntity = this.testRestTemplate.postForEntity("/updateUser", requestEntity, Boolean.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isTrue();

    }
}
