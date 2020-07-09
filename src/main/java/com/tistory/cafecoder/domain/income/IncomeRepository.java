package com.tistory.cafecoder.domain.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByEmail(String email);
    List<Income> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);
}
