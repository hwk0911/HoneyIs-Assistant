package com.tistory.cafecoder.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String size;

    @Builder
    public Size(String size) {
        this.size = size;
    }

    public void update(String size) {
        this.size = size;
    }
}
