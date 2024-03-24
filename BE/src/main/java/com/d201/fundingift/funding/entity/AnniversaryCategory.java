package com.d201.fundingift.funding.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnniversaryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anniversary_category_id", nullable = false)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String name;

}
