package com.example.tdd1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

    /**
     * @RequestBody는 HTTP Request body를 읽고 HttpMessageConverter를 통해서 deserialized시켜 Handler 메소드의 특정 인자 타입의 객체로 변환하기 위한 애노테이션이다.
     * MappingJackson2HttpMessageConverter는 ObjectMapper를 사용해서 Setter가 존재하지 않아도 객체의 필드 자체에 데이터를 전달할 수 있기 때문.
     * @param user
     * @return
     */
    @PostMapping("/postRequestObject")
    public DemoDto checkName(@RequestBody  DemoDto user) {
        log.info(">>>>user: " + user);
        return user;
    }

    @PostMapping("/postRequestQueryString")
    public String checkName(String name, String country) {
        log.info(">>>>name: " + name + " country: " + country);
        return name;
    }
}
