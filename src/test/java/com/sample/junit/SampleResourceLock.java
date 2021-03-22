package com.sample.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

/**
 * SAME_THREAD: Force execution in same thread as the parent node.
 * CONCURRENT : Allow concurrent execution with any other node.
 */
@Execution(ExecutionMode.CONCURRENT)
public class SampleResourceLock {

    @Test
    @ResourceLock(value = "SYSTEM_PROPERTIES", mode = ResourceAccessMode.READ)
    void test1() {
        System.out.println(">1");
        Assertions.assertNull(System.getProperty("my.prop"));
    }

    @Test
    @ResourceLock(value = "SYSTEM_PROPERTIES", mode = ResourceAccessMode.READ_WRITE)
    void test2()  {
        System.out.println(">2");
        System.setProperty("my.prop", "apple");
        Assertions.assertEquals("apple", System.getProperty("my.prop"));
    }

    @Test
    @ResourceLock(value = "SYSTEM_PROPERTIES", mode = ResourceAccessMode.READ_WRITE)
    void test3() {
        System.out.println(">3");
        System.setProperty("my.prop", "banana");
        Assertions.assertEquals("banana", System.getProperty("my.prop"));
    }
}
