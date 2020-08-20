package com.tistory.cafecoder.config.xlsx.dto;

import lombok.Getter;

@Getter
public class XlsxDto {
    private String productName;
    private String color;
    private Long amount;

    public XlsxDto (String productName, String color, Long amount) {
        this.productName = productName;
        this.color = color;
        this.amount = amount;
    }
}
