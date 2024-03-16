package com.d201.fundingift.product.entity;

import com.d201.fundingift._common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Boolean isThumbnail;

    @Column(nullable = false)
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Builder
    private ProductImage(String url, Boolean isThumbnail, Integer sequence, Product product) {
        this.url = url;
        this.isThumbnail = isThumbnail;
        this.sequence = sequence;
        this.product = product;
    }

}
