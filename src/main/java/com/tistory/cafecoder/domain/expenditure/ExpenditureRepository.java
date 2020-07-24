package com.tistory.cafecoder.domain.expenditure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findByEmailAndDateBetweenOrderByDateAsc(String email, LocalDate start, LocalDate end);
}
