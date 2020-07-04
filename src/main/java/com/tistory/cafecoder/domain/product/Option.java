package com.tistory.cafecoder.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Option {
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
    public Option(Long productId, String color, String size, Long amount) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.amount = amount;
    }

    public Option update(Long amount) {
        this.amount -= amount;

        return this;
    }
}
