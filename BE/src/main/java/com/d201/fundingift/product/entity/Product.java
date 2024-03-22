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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'NOT_CHECKED'")
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", referencedColumnName = "product_category_id")
    private ProductCategory productCategory;

    @Builder
    private Product(String name, Integer price, String description, ProductStatus status, ProductCategory productCategory) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
        this.productCategory = productCategory;
    }

}
