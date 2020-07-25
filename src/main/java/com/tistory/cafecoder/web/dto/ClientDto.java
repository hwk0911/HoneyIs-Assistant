package com.tistory.cafecoder.web.dto;

import com.tistory.cafecoder.domain.product.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientDto {
    private Long id;
    private String email;
    private String clientName;
    private String number;
    private String location;

    public ClientDto(String email, String clientName, String number, String location) {
        this.email = email;
        this.clientName = clientName;
        this.number = number;
        this.location =location;
    }

    public Client toEntity() {
        return new Client().builder()
                .email(this.email)
                .clientName(this.clientName)
                .number(this.number)
                .location(this.location)
                .build();
    }
}
