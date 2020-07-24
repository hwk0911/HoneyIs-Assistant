package com.tistory.cafecoder.web;

import com.tistory.cafecoder.service.IncomeService;
import com.tistory.cafecoder.web.dto.IncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class IncomeApiController {

    private final IncomeService incomeService;

    @PostMapping("/api/v1/income/save")
    public Long saveIncome(@RequestBody IncomeDto incomeDto) {
        return this.incomeService.addIncome(incomeDto);
    }

    @PutMapping("/api/v1/income/update")
    public Long updateIncome(@RequestBody IncomeDto updateDto) {
        return this.incomeService.update(updateDto);
    }

    @DeleteMapping("/api/v1/income/delete/{id}")
    public Long deleteIncome(@PathVariable("id") Long id) {
        return this.incomeService.delete(id);
    }
}
