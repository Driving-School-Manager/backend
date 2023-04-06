package com.driving.school;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FullBootstrapTest {

    @Test
    @DisplayName("Integration: application loads properly")
    void contextLoads(ApplicationContext context) {
        assertNotNull(context);
    }

}
