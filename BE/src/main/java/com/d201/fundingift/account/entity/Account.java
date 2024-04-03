package com.d201.fundingift.account.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.consumer.entity.Consumer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(nullable = true, length = 10)
    private String name;

    @Column(nullable = false)
    private String no;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
    private Bank bank;

    @Builder
    private Account(String name, String no, String owner, Boolean isDefault, Consumer consumer, Bank bank) {
        this.name = name;
        this.no = no;
        this.owner = owner;
        this.isDefault = isDefault;
        this.consumer = consumer;
        this.bank = bank;
    }

}
