package com.aston.productservice.mapper;

import com.aston.productservice.dto.ProductDTO;
import com.aston.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Преобразование из сущности Product в DTO ProductDTO
    @Mapping(target = "productName", source = "name")
    @Mapping(target = "productType", source = "type")
    @Mapping(target = "productPrice", source = "price")
    ProductDTO toDto(Product product);

    // Преобразование из DTO ProductDTO обратно в сущность Product
    @Mapping(target = "name", source = "productName")
    @Mapping(target = "type", source = "productType")
    @Mapping(target = "price", source = "productPrice")
    Product toEntity(ProductDTO productDto);
}
