package com.project.ecommerce.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {
        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testSecurityConfiguration() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                        .andExpect(status().isOk());

                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/findAll"))
                        .andExpect(status().isForbidden());
        }


}
