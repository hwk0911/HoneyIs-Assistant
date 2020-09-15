package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLinkRepository extends JpaRepository<ProductLink, Long> {
    List<ProductLink> findByProductNameAndEmail(String name, String email);
}
