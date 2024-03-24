package com.d201.fundingift.product.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.product.entity.status.ProductStatus;
import com.d201.fundingift.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product set deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') where product_id = ?")
public class Product extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Double reviewAvg;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer reviewCnt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'NOT_CHECKED'")
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", referencedColumnName = "product_category_id")
    private ProductCategory productCategory;

    @Builder
    private Product(String name, Integer price, String description, String image, Double reviewAvg, Integer reviewCnt, ProductStatus status, ProductCategory productCategory) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.reviewAvg = reviewAvg;
        this.reviewCnt = reviewCnt;
        this.status = status;
        this.productCategory = productCategory;
    }

    // 리뷰 생성 시
    public void insertReview(Integer star) {
        // 평균
        reviewAvg = (reviewAvg * reviewCnt + star) / (reviewCnt + 1);
        reviewAvg = Math.round(reviewAvg * 100) / 100.0; // 소수점 둘째자리에서 반올림
        // 개수
        reviewCnt += 1;
    }

    // 리뷰 수정 시
    public void updateReview(Integer star) {
        // 평균
        reviewAvg = (reviewAvg * reviewCnt - star) / (reviewCnt - 1);
        reviewAvg = Math.round(reviewAvg * 100) / 100.0; // 소수점 둘째자리에서 반올림
    }

    // 리뷰 삭제 시
    public void deleteReview(Integer star) {
        // 평균
        reviewAvg = (reviewAvg * reviewCnt - star) / (reviewCnt - 1);
        reviewAvg = Math.round(reviewAvg * 100) / 100.0; // 소수점 둘째자리에서 반올림
        // 개수
        reviewCnt -= 1;
    }

}
