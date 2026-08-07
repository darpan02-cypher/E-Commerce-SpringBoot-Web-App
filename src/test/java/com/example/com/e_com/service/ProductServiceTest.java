package com.example.com.e_com.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    Runnable mockRunnable;

    @Test
    @DisplayName("Mockito basic mock + verify works")
    void mockitoBasicWorks() {
        mockRunnable.run();
        verify(mockRunnable).run();
    }
}
