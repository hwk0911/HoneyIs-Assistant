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
    private String email;

    @Column
    private LocalDateTime date;

    @Column
    private Long price;

    @Column
    private String memo;

    @Builder
    public Income (LocalDateTime date, Long price, String memo, String email) {
        this.date = date;
        this.price = price;
        this.memo = memo;
        this.email = email;
    }

    public void update (LocalDateTime date, Long price, String memo) {
        this.date = date;
        this.price = price;
        this.memo = memo;
    }
}
