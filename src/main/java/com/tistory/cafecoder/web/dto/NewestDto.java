package com.tistory.cafecoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

@Getter
@NoArgsConstructor
public class NewestDto {
    private Long clientId;
    private String name;
    private List<String> color;
    private List<String> size;

    public NewestDto(Long clientId, String name, String color, String size) {
        this.clientId = clientId;
        this.name = name;

        this.color = Arrays.asList(color.split(","));

        if(size.equals("")) {
            size = "FREE";
        }

        this.size = Arrays.asList(size.split(","));
    }
}
