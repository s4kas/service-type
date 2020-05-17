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
class PetRestControllerTests {

    private static final String DOG_OK = "Walking a dog named %s";
    private static final String CAT_OK = "Walking a cat named %s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void use_DOG_IfHeaderTypeNotPresent() throws Exception {
        String petName = "rufus";

        mockMvc.perform(get("/")
                .param("petName", "rufus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.format(DOG_OK, petName)));
    }

    @Test
    public void use_DOG_IfHeaderTypeDoesNotMatch() throws Exception {
        String petName = "rufus";
        String headerTypeValue = "something";

        mockMvc.perform(get("/")
                .param("petName", petName)
                .header("type", headerTypeValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.format(DOG_OK, petName)));
    }

    @Test
    public void use_DOG_IfHeaderTypeIsDOG() throws Exception {
        String petName = "rufus";
        String headerTypeValue = "DOG";

        mockMvc.perform(get("/")
                .param("petName", petName)
                .header("type", headerTypeValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.format(DOG_OK, petName)));
    }

    @Test
    public void use_CAT_IfHeaderTypeIsCAT() throws Exception {
        String petName = "miau";
        String headerTypeValue = "CAT";

        mockMvc.perform(get("/")
                .param("petName", petName)
                .header("type", headerTypeValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.format(CAT_OK, petName)));
    }
}
