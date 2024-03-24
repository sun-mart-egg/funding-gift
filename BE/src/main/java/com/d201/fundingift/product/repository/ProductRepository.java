package com.d201.fundingift.product.repository;

import com.d201.fundingift.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품 리스트 조회 - 기본 순
    Slice<Product> findAllSliceByProductCategoryId(Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 리뷰 많은 순
    Slice<Product> findAllSliceByProductCategoryIdOrderByReviewAvgDesc(Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 평점 높은 순
    Slice<Product> findAllSliceByProductCategoryIdOrderByReviewCntDesc(Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 가격 높은 순
    Slice<Product> findAllSliceByProductCategoryIdOrderByPriceDesc(Integer categoryId, Pageable pageable);

    // 상품 리스트 조회 - 가격 낮은 순
    Slice<Product> findAllSliceByProductCategoryIdOrderByPriceAsc(Integer categoryId, Pageable pageable);

}
