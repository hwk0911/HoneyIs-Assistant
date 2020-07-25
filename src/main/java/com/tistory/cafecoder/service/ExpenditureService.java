package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.expenditure.Expenditure;
import com.tistory.cafecoder.domain.expenditure.ExpenditureRepository;
import com.tistory.cafecoder.web.dto.ExpenditureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenditureService {
    private final ExpenditureRepository expenditureRepository;

    @Transactional
    public Long addExpenditure (ExpenditureDto expenditureDto) {
        return expenditureRepository.save(expenditureDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<Expenditure> getMonthList (String email, LocalDate start, LocalDate end) {
        List<Expenditure> expenditureList = this.expenditureRepository.findByEmailAndDateBetweenOrderByDateAsc(email, start, end);

        return expenditureList;
    }

    @Transactional
    public Long update (ExpenditureDto expenditureDto) {
        Expenditure expenditure = findById(expenditureDto.getId());

        expenditure.update(expenditureDto.getPrice(), expenditureDto.getDate(), expenditureDto.getHistory(), expenditureDto.getLocation());

        return expenditure.getId();
    }

    @Transactional
    public Long delete (Long id) {
        Expenditure expenditure = findById(id);
        this.expenditureRepository.delete(expenditure);
        return id;
    }

    public Expenditure findById (Long id) {
        Expenditure expenditure = this.expenditureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("지출내역 조회 중 오류가 발생하였습니다. id: " + id));

        return expenditure;
    }
}
