package com.aston.productservice.config;

import com.aston.productservice.controller.ProductController;
import com.aston.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAllowAccessToAllProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                        .password("password")))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldDenyAccessToOtherEndpoints() throws Exception {
        mockMvc.perform(get("/api/products")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldAllowAccessToOtherEndpointsWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/api/products")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .password("password")))
                .andExpect(status().isOk());
    }
}
