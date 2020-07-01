package com.tistory.cafecoder.domain.income;

import com.tistory.cafecoder.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Income extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime date;

    @Column
    private Long price;

    @Builder
    public Income (LocalDateTime date, Long price) {
        this.date = date;
        this.price = price;
    }

    public Income update (LocalDateTime modifiedDate) {
        this.date = modifiedDate;

        return this;
    }

    public Income update (Long modifiedPrice) {
        this.price = modifiedPrice;

        return this;
    }
}
