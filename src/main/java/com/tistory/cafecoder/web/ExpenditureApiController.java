package com.tistory.cafecoder.web;

import com.tistory.cafecoder.service.ExpenditureService;
import com.tistory.cafecoder.web.dto.ExpenditureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ExpenditureApiController {

    private final ExpenditureService expenditureService;

    @PostMapping("/api/v1/expenditure/save")
    public Long expenditureSave(@RequestBody ExpenditureDto expenditureDto) {
        return expenditureService.addExpenditure(expenditureDto);
    }

    @PutMapping("/api/v1/expenditure/update")
    public Long expenditureUpdate(@RequestBody ExpenditureDto update) {
        return expenditureService.update(update);
    }

    @DeleteMapping("/api/v1/expenditure/delete/{id}")
    public Long expenditureDelete(@PathVariable("id") Long id) {
        return expenditureService.delete(id);
    }
}
