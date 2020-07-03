package com.tistory.cafecoder.domain.expenditure;

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
public class Expenditure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private Long price;

    @Column
    private LocalDateTime date;

    @Column
    private String history;

    @Column
    private String location;

    @Builder
    public Expenditure(Long price, LocalDateTime date, String history, String location, String email) {
        this.price = price;
        this.date = date;
        this.history = history;
        this.location = location;
        this.email = email;
    }

    public void update (Long price, LocalDateTime date, String history, String location) {
        this.price = price;
        this.date = date;
        this.history = history;
        this.location = location;
    }
}
