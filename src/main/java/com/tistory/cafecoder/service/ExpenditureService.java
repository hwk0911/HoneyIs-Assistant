package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.expenditure.Expenditure;
import com.tistory.cafecoder.domain.expenditure.ExpenditureRepository;
import com.tistory.cafecoder.web.dto.ExpenditureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Expenditure> getExpenditureList () {
        return expenditureRepository.findAll();
    }

    @Transactional
    public void update (Long id, ExpenditureDto expenditureDto) {
        Expenditure expenditure = findById(id);

        expenditure.update(expenditureDto.getPrice(), expenditureDto.getDate(), expenditureDto.getHistory(), expenditureDto.getLocation());
    }

    @Transactional
    public void delete (Long id) {
        Expenditure expenditure = findById(id);

        this.expenditureRepository.delete(expenditure);
    }

    public Expenditure findById (Long id) {
        Expenditure expenditure = this.expenditureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("지출내역 조회 중 오류가 발생하였습니다. id: " + id));

        return expenditure;
    }
}
