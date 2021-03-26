package com.sample.junit;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SampleDynamicTest {

    @TestFactory
    Collection<DynamicTest> dynamicTests() {
        return Arrays.asList(
            dynamicTest("java", () -> assertTrue(true)),
            dynamicTest("spring", new CustomExecutable())
//            dynamicTest("Exception", () -> {throw new RuntimeException("Runtime Exception Test.");})
        );
    }
}

class CustomExecutable implements Executable {
    @Override
    public void execute() throws Throwable {
        System.out.println("~~~ ok ~~~~");
    }
}