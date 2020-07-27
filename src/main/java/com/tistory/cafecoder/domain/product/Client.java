package com.tistory.cafecoder.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String number;

    @Column
    private String location;

    @Builder
    public Client(String email, String name, String number, String location) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.location = location;
    }

    public void update(String name, String number, String location) {
        this.name = name;
        this.number = number;
        this.location = location;
    }
}
