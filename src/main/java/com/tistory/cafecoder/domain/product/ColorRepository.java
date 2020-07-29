package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findByColor(String color);
}
