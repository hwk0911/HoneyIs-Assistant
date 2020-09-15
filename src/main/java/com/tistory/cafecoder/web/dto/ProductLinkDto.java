package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductLinkDto {
    private String productName;
    private String targetName;

    public ProductLinkDto (String productName, String targetName) {
        this.productName = productName;
        this.targetName = targetName;
    }
}
