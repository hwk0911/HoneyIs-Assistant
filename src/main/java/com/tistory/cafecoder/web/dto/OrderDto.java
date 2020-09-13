package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class OrderDto implements Serializable {
    private Long id;
    private String productName;
    private String color;
    private String size;
    private Long orderAmount;
    private Long stockAmount;
    private String scarce;

    public OrderDto(Long id, String productName, String color, String size, Long orderAmount, Long stockAmount) {
        this.id = id;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.orderAmount = orderAmount;
        this.stockAmount = stockAmount;

        this.setScarce(this.orderAmount, this.stockAmount);
    }

    private void setScarce (Long orderAmount, Long stockAmount) {
        if(orderAmount >= stockAmount) {
            this.scarce = "red";
        }
        else if(orderAmount > stockAmount / 2) {
            this.scarce = "green";
        }
        else {
            this.scarce = "";
        }
    }
}
