package com.tistory.cafecoder.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContainsAndEmail(String searchWord, String email);
    List<Client> findByEmail(String email);
    List<ClientMapping> findIdByEmail(String email);

    Client findByIdAndEmail(Long id, String email);
    Client findByName(String name);
    Client findByNameAndEmail (String name, String email);
}
