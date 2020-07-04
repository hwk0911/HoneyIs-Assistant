package com.tistory.cafecoder.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long clientId;

    @Column
    private String productName;

    @Builder
    public Product(Long clientId, String productName) {
        this.clientId = clientId;
        this.productName = productName;
    }
}
