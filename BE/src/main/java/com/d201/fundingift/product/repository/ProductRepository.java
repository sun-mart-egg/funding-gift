package com.d201.fundingift.product.repository;

import com.d201.fundingift.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품 리스트 조회 - 기본 순
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId and p.status = 'ACTIVE' and p.deletedAt is null")
    Slice<Product> findAllSliceByProductCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 리뷰 많은 순
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId and p.status = 'ACTIVE' and p.deletedAt is null " +
            "order by p.reviewCnt desc")
    Slice<Product> findAllSliceByProductCategoryIdOrderByReviewAvgDesc(@Param("categoryId") Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 평점 높은 순
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId and p.status = 'ACTIVE' and p.deletedAt is null " +
            "order by p.reviewAvg desc")
    Slice<Product> findAllSliceByProductCategoryIdOrderByReviewCntDesc(@Param("categoryId") Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 가격 높은 순
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId and p.status = 'ACTIVE' and p.deletedAt is null " +
            "order by p.price desc")
    Slice<Product> findAllSliceByProductCategoryIdOrderByPriceDesc(@Param("categoryId") Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 가격 낮은 순
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId and p.status = 'ACTIVE' and p.deletedAt is null " +
            "order by p.reviewCnt asc")
    Slice<Product> findAllSliceByProductCategoryIdOrderByPriceAsc(@Param("categoryId") Integer categoryId, Pageable pageable);

}
