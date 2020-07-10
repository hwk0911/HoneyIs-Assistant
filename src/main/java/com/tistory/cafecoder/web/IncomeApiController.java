package com.tistory.cafecoder.web;

import com.tistory.cafecoder.service.IncomeService;
import com.tistory.cafecoder.web.dto.IncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class IncomeApiController {

    private final IncomeService incomeService;

    @PostMapping("/api/v1/saveincome")
    public Long saveIncome(@RequestBody IncomeDto incomeDto) {
        return this.incomeService.addIncome(incomeDto);
    }
}
