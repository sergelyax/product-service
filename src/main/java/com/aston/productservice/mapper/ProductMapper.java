package com.aston.productservice.mapper;

import com.aston.productservice.dto.ProductDTO;
import com.aston.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productName", source = "name")
    @Mapping(target = "productType", source = "type")
    @Mapping(target = "productPrice", source = "price")
    ProductDTO toDto(Product product);

    @Mapping(target = "name", source = "productName")
    @Mapping(target = "type", source = "productType")
    @Mapping(target = "price", source = "productPrice")
    Product toEntity(ProductDTO productDto);
}
