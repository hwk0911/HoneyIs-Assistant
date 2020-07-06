package com.tistory.cafecoder.domain.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByEmail(String email);
    List<Income> findByDateBetween(LocalDateTime start, LocalDateTime end);
}
