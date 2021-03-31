package com.example.tdd1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class BaseTest {
    private AutoCloseable closeable;

    @BeforeAll
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
