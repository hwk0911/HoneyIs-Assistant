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
    private String name;

    @Column
    private Long colorId;

    @Column
    private Long sizeId;

    @Column
    private String code;

    @Column
    private Long amount;

    @Builder
    public Product(Long clientId, String name, Long colorId, Long sizeId) {
        this.amount = 0L;

        this.clientId = clientId;
        this.name = name;
        this.colorId = colorId;
        this.sizeId = sizeId;

        this.setCode();
    }

    public void update(String name, Long colorId, Long sizeId, Long amount) {
        this.name = name;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.amount = amount;

        this.setCode();
    }

    private void setCode() {
        this.code = new StringBuilder(this.id + "-" + this.colorId + "-" + this.sizeId).toString();
    }
}
