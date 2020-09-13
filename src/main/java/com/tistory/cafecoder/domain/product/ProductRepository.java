package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByNameAndColorIdAndEmail(String name, Long colorId, String email);
    List<Product> findByNameAndEmail(String name, String email);
    List<Product> findByName(String name);
    List<Product> findByClientId(Long clientId);

    List<Product> findByNameAndClientId(String name, Long clientId);
}
