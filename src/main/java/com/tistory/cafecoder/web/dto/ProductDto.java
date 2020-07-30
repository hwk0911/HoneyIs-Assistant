package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {
    private String productName;
    private String color;
    private String size;
    private Long amount;

    public ProductDto(String productName, String color, String size, Long amount) {
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.amount = amount;
    }
}
