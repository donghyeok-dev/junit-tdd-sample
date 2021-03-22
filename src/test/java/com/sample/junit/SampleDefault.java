package com.sample.junit;

import com.example.tdd1.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SampleDefault.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SampleDefault {
    UserDto user;

    public boolean checkString(String str) {
        return !str.isBlank() && str.length() > 3;
    }

    @BeforeAll
    public  void beforeAllTest() {
        System.out.println("beforeAllTest");
        //[실행 우선순위 1] static method로 만들어야되고,
        // @TestInstance가 선언되어 있어야 된다.
    }

    @BeforeEach
    public void beforeEachTest() {
        System.out.println("beforeEachTest");
        //[실행 우선순위 2] 각 @Test Method가 실행되기 전 실행 된다.
        user = UserDto.builder().userId("Java").userName("World").build();
    }

    test

    @Test
    @DisplayName("기본적인 테스트 메소드")
    void test1() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    @DisplayName("assertTrue 테스트")
    public void assertTrueTest() {
        Assertions.assertTrue(checkString(user.getUserId()));
        Assertions.assertTrue(user.getUserId().equalsIgnoreCase("java"), "userId가 java이다.");
    }

    @Test
    @DisplayName("assertFalse 테스트")
    void assertFalseTest() {
        Assertions.assertFalse(user.getUserName().length() < 5, "userName이 5자리 보다 작다.");
    }

    @Test
    @DisplayName("assertEquals 테스트")
    void assertEqualsTest() {
        Assertions.assertEquals(user.getUserId(), "Java");
        Assertions.assertEquals(user.getUserName(), "World", "userName이 World가 아니다.");
    }

    void test_is_user_check() {
        Assertions.assertEquals(1, 1);
    }

    @AfterAll
    public static void afterAllTest() {
        System.out.println("afterAllTest");
        //[실행 우선순위 1] static method로 만들어야되고,
        // @TestInstance(TestInstance.Lifecycle.PER_CLASS)가 선언되어 있어야 된다.
    }
}

