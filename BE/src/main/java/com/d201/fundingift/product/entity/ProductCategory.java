package com.d201.fundingift.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product_category set deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') where product_category_id = ?")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id", nullable = false)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Builder
    private ProductCategory(String name) {
        this.name = name;
    }

}
