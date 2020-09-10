package com.tistory.cafecoder.domain.product;

import com.tistory.cafecoder.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long clientId;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private Long colorId;

    @Column
    private Long sizeId;

    @Column
    private Long amount;

    @Builder
    public Product(Long clientId, String email, String name, Long colorId, Long sizeId) {
        this.clientId = clientId;
        this.email = email;
        this.name = name;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.amount = 0L;
    }

    public void update(String name, Long colorId, Long sizeId, Long amount, Long clientId) {
        this.name = name;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.amount = amount;
        this.clientId = clientId;
    }
}
