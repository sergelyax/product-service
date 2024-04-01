package com.aston.productservice.service;

import com.aston.productservice.dto.ProductDTO;
import com.aston.productservice.exception.ProductNotFoundException;
import com.aston.productservice.mapper.ProductMapper;
import com.aston.productservice.model.Product;
import com.aston.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllProducts() {
        // Setup
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductDTO());

        // Execute
        List<ProductDTO> products = productService.findAllProducts();

        // Verify
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository).findAll();
    }

    @Test
    void findProductById() {
        // Setup
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductDTO());

        // Execute
        Optional<ProductDTO> product = productService.findProductById(1L);

        // Verify
        assertTrue(product.isPresent());
        verify(productRepository).findById(1L);
    }

    @Test
    void createProduct() {
        // Setup
        ProductDTO productDto = new ProductDTO();
        Product product = new Product();
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        // Execute
        ProductDTO createdProduct = productService.createProduct(productDto);

        // Verify
        assertNotNull(createdProduct);
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct() {
        // Setup
        ProductDTO productDto = new ProductDTO();
        Product product = new Product();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        // Execute
        Optional<ProductDTO> updatedProduct = productService.updateProduct(1L, productDto);

        // Verify
        assertTrue(updatedProduct.isPresent());
        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct_WhenProductExists() {
        // Setup
        when(productRepository.existsById(anyLong())).thenReturn(true);

        // Execute
        boolean isDeleted = productService.deleteProduct(1L);

        // Verify
        assertTrue(isDeleted);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_WhenProductDoesNotExist_ShouldThrowException() {
        // Setup
        long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Execute & Verify
        ProductNotFoundException thrown = assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));

        // Проверяем, что сообщение исключения соответствует ожидаемому
        assertEquals("Product with ID " + productId + " not found.", thrown.getMessage());
    }


    @Test
    void findProductsByNameContaining() {
        // Setup
        when(productRepository.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(Arrays.asList(new Product(), new Product()));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductDTO());

        // Execute
        List<ProductDTO> products = productService.findProductsByNameContaining("test");

        // Verify
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository).findByNameContainingIgnoreCase("test");
    }

}
