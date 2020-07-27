package com.tistory.cafecoder.web.dto;

import com.tistory.cafecoder.domain.product.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String email;
    private String name;
    private String number;
    private String location;

    public ClientDto(String email, String name, String number, String location) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.location =location;
    }

    public Client toEntity() {
        return new Client().builder()
                .email(this.email)
                .name(this.name)
                .number(this.number)
                .location(this.location)
                .build();
    }
}
