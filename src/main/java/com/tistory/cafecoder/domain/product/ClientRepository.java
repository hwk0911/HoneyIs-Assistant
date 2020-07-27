package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContains(String searchWord);
}
