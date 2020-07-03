package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.income.Income;
import com.tistory.cafecoder.domain.income.IncomeRepository;
import com.tistory.cafecoder.web.dto.IncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    @Transactional(readOnly = true)
    public List<Income> getIncomeList (String email) {
        List<Income> incomeList = this.incomeRepository.findByEmail(email);

        return incomeList;
    }

    @Transactional
    public Long addIncome (IncomeDto incomeDto) {
        return incomeRepository.save(incomeDto.toEntity()).getId();
    }

    @Transactional
    public void update (Long id, IncomeDto updateDto) {
        Income income = findById(id);

        income.update(updateDto.getDate(), updateDto.getPrice(), updateDto.getMemo());
    }

    @Transactional
    public void delete (Long id) {
        Income income = findById(id);

        incomeRepository.delete(income);
    }

    public Income findById(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("지출내역 조회 중 오류가 발생하였습니다. id: " + id));

        return income;
    }
}
