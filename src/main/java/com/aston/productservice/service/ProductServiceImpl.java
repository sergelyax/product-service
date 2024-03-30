package com.aston.productservice.service;

import com.aston.productservice.dto.ProductDTO;
import com.aston.productservice.mapper.ProductMapper;
import com.aston.productservice.model.Product;
import com.aston.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDto) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setProductName(productDto.getProductName());
                    product.setProductType(productDto.getProductType());
                    product.setProductPrice(productDto.getProductPrice());
                    return productMapper.toDto(productRepository.save(product));
                });
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
