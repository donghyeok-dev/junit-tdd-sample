package com.example.tdd1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class DemoController {

    @GetMapping("/simple")
    public String testMvcResult(String name) {
        return name;
    }

    @GetMapping("/testAsync")
    @Async("mvcTaskExecutor")
    public CompletableFuture<String> testAsync(String name)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            randomDelay();
            return name;
        });
    }

    private void randomDelay()
    {
        try
        {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    @PostMapping("/requestMultipart")
    public ResponseEntity<String> requestMultipart(@RequestParam("file") MultipartFile file) {

        return file.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * @RequestBody는 HTTP Request *body*를 읽고 HttpMessageConverter를 통해서 deserialized시켜 Handler 메소드의 특정 인자 타입의 객체로 변환하기 위한 애노테이션이다.
     * MappingJackson2HttpMessageConverter는 ObjectMapper를 사용해서 Setter가 존재하지 않아도 객체의 필드 자체에 데이터를 전달할 수 있기 때문.
     * Get방식은 body가 존재하지 않기 때문에 적용할 수 있음.
     * @param user
     * @return
     */
    @PostMapping("/requestPostDto")
    public List<DemoDto> requestPostDto(@RequestBody  DemoDto user) {
        List<DemoDto> resultList = new ArrayList<>();

        String[] userNames = new String[]{"파", "마늘", "양파"};

        Arrays.stream(userNames)
//                .map(s -> s.equals("김태희") ? null : s)
                .forEach(value -> resultList.add(DemoDto.builder()
                       .userName(value)
                       .userId(user.getUserId())
                       .userPassword(user.getUserPassword())
                       .build()));

        return resultList;
    }

    @PostMapping("/requestPostString")
    public String postRequestString(String name, String country) {
        return name;
    }

    @GetMapping("/requestGetDto")
    public DemoDto requestGetDto(DemoDto user) {
        return user;
    }


}


