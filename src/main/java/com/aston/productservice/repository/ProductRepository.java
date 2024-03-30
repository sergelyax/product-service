package com.aston.productservice.repository;

import com.aston.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Сюда дополнительные методы запросов, если они нужны
  //для поиска продуктов по названию: // List<Product> findByProductNameContainingIgnoreCase(String productName);
}
