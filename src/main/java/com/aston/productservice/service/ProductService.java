package com.aston.productservice.service;

import com.aston.productservice.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAllProducts();
    Optional<ProductDTO> findProductById(Long id);
    ProductDTO createProduct(ProductDTO productDto);
    Optional<ProductDTO> updateProduct(Long id, ProductDTO productDto);
    void deleteProduct(Long id);
}
