package com.example.tdd1;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Tdd1ApplicationTests {

    @Test
    @Order(2)
    @DisplayName("기본 테스트")
    void test1() {
        assertEquals(1, 1);
    }

    @Test
    @Order(1)
    @DisplayName("List 테스트")
    void test2() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        /*
        Assertions.assertEquals(1, numbers.get(0));
        Assertions.assertEquals(3, numbers.get(1));
        Assertions.assertEquals(3, numbers.get(1));
        Assertions.assertEquals(3, numbers.get(1));
        */

        Assertions.assertAll(() -> assertEquals(1, numbers.get(0)),
                            () -> assertEquals(1, numbers.get(0)),
                            () -> assertEquals(1, numbers.get(0))
                );
    }

    @ParameterizedTest
    @DisplayName("parameter 테스트")
    @ValueSource(strings = {"김동혁", "박정현", "김정훈"})
    void test3(String userName) {
//        assertEquals(userName, "박정현");
        assertEquals(userName, userName);
    }

    @Test
    @DisplayName("Exception 테스트")
    void test4() {
        DemoService ds = new DemoService();
        assertThrows(RuntimeException.class, ds::getData);
    }


    @Test
    @DisplayName("Disabled 테스트")
    @Disabled
    void test5() {
        Assertions.assertEquals(1, 2);

    }

    @Nested
    @DisplayName("사용자를 저장할때는")
    class test6 {
        UserDto userDto = UserDto.builder().userId("java").userName("study").build();

        @Test
        @DisplayName("ID와 Name이 빈값이 아니고")
        void name() {
            assertFalse(Strings.isBlank(userDto.getUserId()), "ID가 빈 값입니다.");
            assertFalse(Strings.isBlank(userDto.getUserName()), "Name이 빈 값입니다.");
        }

    }

}
