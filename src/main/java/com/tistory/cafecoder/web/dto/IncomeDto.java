package com.tistory.cafecoder.web.dto;

import com.tistory.cafecoder.domain.income.Income;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class IncomeDto {
    private Long id;
    private String email;
    private LocalDateTime date;
    private Long price;
    private String memo;

    public IncomeDto(Income entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.date = entity.getDate();
        this.price = entity.getPrice();
        this.memo = entity.getMemo();
    }

    public Income toEntity() {
        return new Income().builder()
                .email(this.email)
                .date(this.date)
                .price(this.price)
                .memo(this.memo)
                .build();
    }
}
