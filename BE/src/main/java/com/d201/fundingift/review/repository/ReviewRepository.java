package com.d201.fundingift.review.repository;

import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 상품 별 리뷰 리스트 조회 - 최신 순
    @Query("select r from Review r " +
            "where r.product = :product and r.status = 'ACTIVE' and r.deletedAt is null " +
            "order by r.createdAt desc")
    Slice<Review> findAllSliceByProductIdOrderByCreatedAtDesc(@Param("product") Product product, Pageable pageable);

    // 상품 별 리뷰 리스트 조회 - 별점 높은 순
    @Query("select r from Review r " +
            "where r.product = :product and r.status = 'ACTIVE' and r.deletedAt is null " +
            "order by r.star desc")
    Slice<Review> findAllSliceByProductIdOrderByStarDesc(@Param("product") Product product, Pageable pageable);

    // 상품 별 리뷰 리스트 조회 - 별점 낮은 순
    @Query("select r from Review r " +
            "where r.product = :product and r.status = 'ACTIVE' and r.deletedAt is null " +
            "order by r.star asc")
    Slice<Review> findAllSliceByProductIdOrderByStarAsc(@Param("product") Product product, Pageable pageable);

}
