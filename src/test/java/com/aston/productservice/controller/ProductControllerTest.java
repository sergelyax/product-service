package com.aston.productservice.controller;

import com.aston.productservice.dto.ProductDTO;
import com.aston.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDTO product;

    @BeforeEach
    void setUp() {
        product = new ProductDTO(1L, "Test Product", "Food", 9.99);
    }

    @Test
    @WithMockUser
    public void getAllProducts_ShouldReturnProducts() throws Exception {
        Mockito.when(productService.findAllProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(product.getProductName())));
    }

    @Test
    @WithMockUser
    public void getProductById_ShouldReturnProduct() throws Exception {
        Mockito.when(productService.findProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getProductName())));
    }

    @Test
    @WithMockUser
    public void createProduct_ShouldReturnCreatedProduct() throws Exception {
        Mockito.when(productService.createProduct(Mockito.any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content("{\"name\":\"Test Product\",\"type\":\"Food\",\"price\":9.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getProductName())));
    }

    @Test
    @WithMockUser
    public void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        Mockito.when(productService.updateProduct(Mockito.eq(1L), Mockito.any(ProductDTO.class))).thenReturn(Optional.of(product));

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content("{\"name\":\"Updated Test Product\",\"type\":\"Food\",\"price\":10.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getProductName())));
    }

    @Test
    @WithMockUser
    public void deleteProduct_ShouldReturnNoContent() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}
