package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Size findBySize(String size);
    SizeMapping findSizeById(Long id);
}
