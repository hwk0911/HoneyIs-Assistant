package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String productName;
    private String color;
    private String size;
    private Long amount;

    public ProductDto(Long id, String productName, String color, String size, Long amount) {
        this.id = id;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.amount = amount;
    }
}
