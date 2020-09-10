package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UndefinedStockDto {
    private String productName;
    private String clientName;

    public UndefinedStockDto (String productName, String clientName) {
        this.productName = productName;
        this.clientName = clientName;
    }
}
