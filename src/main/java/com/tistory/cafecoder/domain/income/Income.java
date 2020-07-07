package com.tistory.cafecoder.domain.income;

import com.tistory.cafecoder.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Income extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private LocalDate date;

    @Column
    private Long price;

    @Column
    private String memo;

    @Builder
    public Income (LocalDate date, Long price, String memo, String email) {
        this.date = date;
        this.price = price;
        this.memo = memo;
        this.email = email;
    }

    public void update (LocalDate date, Long price, String memo) {
        this.date = date;
        this.price = price;
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "[" + this.getId() + " " + this.getEmail() + " " + this.getDate() + " " + this.getPrice() + " " + this.getMemo() + "]";
    }
}
