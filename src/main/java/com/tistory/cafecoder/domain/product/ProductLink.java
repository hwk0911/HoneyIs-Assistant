package com.tistory.cafecoder.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ProductLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String productName;

    @Column
    private String targetName;

    @Column
    private String email;

    @Builder
    public ProductLink(String productName, String targetName, String email) {
        this.productName = productName;
        this.targetName = targetName;
        this.email = email;
    }

    public void update(String targetName) {
        this.targetName = targetName;
    }
}
