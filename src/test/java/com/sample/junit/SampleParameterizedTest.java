package com.sample.junit;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

public class SampleParameterizedTest {

    @ParameterizedTest
    @DisplayName("매개변수 테스트")
    @ValueSource(strings={"Java", "Spring", "JUnit5"})
    public void test(String param) {
        Assertions.assertTrue(() -> {
            return Objects.requireNonNull(param).length() > 3; });
    }

}
