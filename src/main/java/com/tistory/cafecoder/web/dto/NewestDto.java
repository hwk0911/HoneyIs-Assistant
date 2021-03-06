package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class NewestDto {
    private Long clientId;
    private String name;
    private String color;
    private String size;
    private Long amount;

    public NewestDto(Long clientId, String name, String color, String size, Long amount) {
        this.clientId = clientId;
        this.name = name;
        this.color = color;
        this.size = size;
        this.amount = amount;
    }
}
