package com.d201.fundingift.review.repository;

import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r " +
            "where r.id = :reviewId " +
            "and r.status = 'ACTIVE' and r.deletedAt is null ")
    Optional<Review> findById(@Param("reviewId") Long reviewId);

    // 상품 별 리뷰 리스트 조회
    @Query("select r from Review r " +
            "where r.product = :product " +
            "and r.status = 'ACTIVE' and r.deletedAt is null")
    Slice<Review> findAllSliceByProduct(
            @Param("product") Product product, Pageable pageable);

    // 상품, 상품 옵션 별 리뷰 리스트 조회
    @Query("select r from Review r " +
            "where r.product = :product and r.productOption = :productOption " +
            "and r.status = 'ACTIVE' and r.deletedAt is null")
    Slice<Review> findAllSliceByProductAndOption(
            @Param("product") Product product, @Param("productOption") ProductOption productOption, Pageable pageable);

}
