package com.d201.fundingift.funding.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.funding.entity.status.FundingStatus;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer sumPrice;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer minPrice;

    @Column(nullable = false)
    private Integer targetPrice;

    @Column(nullable = false)
    private LocalDate anniversaryDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false, length = 10)
    private String accountBank;

    @Column(nullable = false, length = 50)
    private String accountNo;

    @Column(nullable = false, length = 50)
    private String defaultAddr;

    @Column(nullable = true, length = 50)
    private String detailAddr;

    @Column(nullable = false, length = 10)
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'NOT_STARTED'")
    private FundingStatus fundingStatus;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anniversary_category_id", referencedColumnName = "anniversary_category_id")
    private AnniversaryCategory anniversaryCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", referencedColumnName = "product_option_id")
    private ProductOption productOption;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();

    @Builder
    private Funding(Integer sumPrice, Integer minPrice, Integer targetPrice, LocalDate anniversaryDate, LocalDateTime startDate, LocalDateTime endDate, String title, String content, String accountBank, String accountNo, String defaultAddr, String detailAddr, String zipCode, FundingStatus fundingStatus, Boolean isPrivate, Consumer consumer, AnniversaryCategory anniversaryCategory, Product product, ProductOption productOption) {
        this.sumPrice = sumPrice;
        this.minPrice = minPrice;
        this.targetPrice = targetPrice;
        this.anniversaryDate = anniversaryDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
        this.accountBank = accountBank;
        this.accountNo = accountNo;
        this.defaultAddr = defaultAddr;
        this.detailAddr = detailAddr;
        this.zipCode = zipCode;
        this.fundingStatus = fundingStatus;
        this.isPrivate = isPrivate;
        this.consumer = consumer;
        this.anniversaryCategory = anniversaryCategory;
        this.product = product;
        this.productOption = productOption;
    }

}
