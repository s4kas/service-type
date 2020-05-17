package com.bmp.tests.servicetype;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DummyRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ok_executeNotInjectedController() throws Exception {
        mockMvc.perform(get("/dummy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
