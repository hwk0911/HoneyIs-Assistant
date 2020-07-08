package com.tistory.cafecoder.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TermIncomeDto {
    private LocalDate start;
    private LocalDate end;

    @Builder
    public TermIncomeDto (LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
}
