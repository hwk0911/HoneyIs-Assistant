package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.income.Income;
import com.tistory.cafecoder.domain.income.IncomeRepository;
import com.tistory.cafecoder.web.dto.IncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    @Transactional
    public Long addIncome (IncomeDto incomeDto) {
        return incomeRepository.save(incomeDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<Income> getMonthList (String email, LocalDate start, LocalDate end) {
        List<Income> incomeListToMonth = this.incomeRepository.findByEmailAndDateBetweenOrderByDateAsc(email, start, end);

        return incomeListToMonth;
    }

    @Transactional
    public Long update (IncomeDto updateDto) {
        Income income = findById(updateDto.getId());

        if(income != null) {
            income.update(updateDto.getDate(), updateDto.getPrice(), updateDto.getMemo());
        }

        return income.getId();
    }

    @Transactional
    public Long delete (Long id) {
        Income income = findById(id);
        incomeRepository.delete(income);
        return id;
    }

    public Income findById(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회 중 오류가 발생하였습니다. id: " + id));

        return income;
    }
}
