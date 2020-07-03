package com.tistory.cafecoder.web.dto;

import com.tistory.cafecoder.domain.expenditure.Expenditure;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ExpenditureDto {
    private Long id;
    private String email;
    private Long price;
    private LocalDateTime date;
    private String history;
    private String location;

    public ExpenditureDto (Expenditure entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.price = entity.getPrice();
        this.date = entity.getDate();
        this.history = entity.getHistory();
        this.location = entity.getLocation();
    }

    public Expenditure toEntity () {
        return new Expenditure().builder()
                .email(this.email)
                .price(this.price)
                .date(this.date)
                .history(this.history)
                .location(this.location)
                .build();
    }
}
