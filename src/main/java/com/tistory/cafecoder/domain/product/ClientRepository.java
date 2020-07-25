package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByName(String searchWord);
}
