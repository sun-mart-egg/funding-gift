package com.d201.fundingift.review.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.review.entity.status.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE review set deleted_at = DATE_ADD(NOW(), INTERVAL 9 HOUR) where review_id = ?")
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer star;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String image1;

    @Column(nullable = true)
    private String image2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'ACTIVE'")
    private ReviewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", referencedColumnName = "product_option_id")
    private ProductOption productOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    private Consumer consumer;

    @Builder
    private Review(Integer star, String content, String image1, String image2, ReviewStatus status, Product product, ProductOption productOption, Consumer consumer) {
        this.star = star;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.status = status;
        this.product = product;
        this.productOption = productOption;
        this.consumer = consumer;
    }

}
