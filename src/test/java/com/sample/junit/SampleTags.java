package com.sample.junit;

import com.example.tdd1.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SampleTags {
    UserDto user;

    public boolean checkString(String str) {
        return !str.isBlank() && str.length() > 3;
    }

    @BeforeAll
    public static void beforeAllTest() {
        //[실행 우선순위 1] static method로 만들어야되고, @TestInstance가 선언되어 있어야 된다.
    }

    @BeforeEach
    public void beforeEachTest() {
        //[실행 우선순위 2] 각 @Test Method가 실행되기 전 실행 된다.
        user = UserDto.builder().userId("Java").userName("World").build();
    }

    @ModuleA
    @DisplayName("assertTrue 테스트")
    public void assertTrueTest() {
        Assertions.assertTrue(checkString(user.getUserId()));
        Assertions.assertTrue(user.getUserId().equalsIgnoreCase("java"), "userId가 java이다.");
    }

    @ModuleB
    @DisplayName("assertFalse 테스트")
    void assertFalseTest() {
        Assertions.assertFalse(user.getUserName().length() < 5, "userName이 5자리 보다 작다.");
    }

    @ModuleC
    @DisplayName("assertEquals 테스트")
    void assertEqualsTest() {
        Assertions.assertEquals(user.getUserId(), "Java");
        Assertions.assertEquals(user.getUserName(), "World", "userName이 World가 아니다.");
    }
}

