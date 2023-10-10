package com.acidtango.itxbackendtools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("mongo")
class InditexBackendToolsApplicationTests {

    @Test
    void context_loads() {
    }

}
