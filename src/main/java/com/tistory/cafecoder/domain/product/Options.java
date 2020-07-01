package com.tistory.cafecoder.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@NoArgsConstructor
@Entity
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long productId;

    @Column
    private String color;

    @Column
    private String size;

    @Column
    private Long amount;

    @Builder
    public Options (Long productId, String color, String size, Long amount) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.amount = amount;
    }

    public Options update(Long amount) {
        this.amount -= amount;

        return this;
    }
}
