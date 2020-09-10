package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByNameAndColorId(String name, Long colorId);
    List<Product> findByName(String name);
    List<Product> findByClientId(Long clientId);

    List<Product> findByNameAndClientId(String name, Long clientId);
}
