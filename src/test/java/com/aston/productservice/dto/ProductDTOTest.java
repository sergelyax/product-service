package com.aston.productservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldHaveNoViolations() {
        // given
        ProductDTO productDTO = new ProductDTO(1L, "Product Name", "Product Type", 10.0);

        // when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldDetectInvalidProductName() {
        // given
        ProductDTO productDTO = new ProductDTO(1L, "", "Product Type", 10.0); // Empty name is invalid

        // when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);

        // then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldDetectInvalidPrice() {
        // given
        ProductDTO productDTO = new ProductDTO(1L, "Product Name", "Product Type", -10.0); // Negative price is invalid

        // when
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);

        // then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    // ... можно добавить больше тестов для других валидаций
}
