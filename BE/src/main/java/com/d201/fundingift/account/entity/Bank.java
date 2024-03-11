package com.d201.fundingift.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bank {

    @Id
    @Column(name = "bank_id", nullable = false)
    private String id;

    @Column(nullable = false, length = 10)
    private String name;

    @Builder
    private Bank(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
