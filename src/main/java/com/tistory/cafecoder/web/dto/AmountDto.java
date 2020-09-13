package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AmountDto {
    private Long orderAmount;
    private Long stockAmount;

    public AmountDto(Long orderAmount, Long stockAmount) {
        this.orderAmount = orderAmount;
        this.stockAmount = stockAmount;
    }

    public void addOrderAmount (Long addAmount) {
        this.orderAmount += addAmount;
    }
}
