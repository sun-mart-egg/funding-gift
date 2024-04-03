package com.d201.fundingift.product.repository;

import com.d201.fundingift.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p " +
            "where p.id = :productId and p.status = 'ACTIVE' and p.deletedAt is null")
    Optional<Product> findById(@Param("productId") Long productId);

    // 전체 목록
    @Query("select p from Product p " +
            "where p.status = 'ACTIVE' and p.deletedAt is null")
    Slice<Product> findAllSlice(Pageable pageable);

    // 전체 목록 + 검색어
    @Query("select p from Product p " +
            "where (p.name like %:keyword% or p.description like %:keyword% or p.productCategory.name like %:keyword%) " +
            "and p.status = 'ACTIVE' and p.deletedAt is null")
    Slice<Product> findAllSliceByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 카테고리 별 목록
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId " +
            "and p.status = 'ACTIVE' and p.deletedAt is null")
    Slice<Product> findAllSliceByCategoryId(@Param("categoryId") Integer productCategoryId, Pageable pageable);

    // 카테고리 별 목록 + 검색어
    @Query("select p from Product p " +
            "where p.productCategory.id = :categoryId " +
            "and (p.name like %:keyword% or p.description like %:keyword% or p.productCategory.name like %:keyword%) " +
            "and p.status = 'ACTIVE' and p.deletedAt is null")
    Slice<Product> findAllSliceByCategoryIdAndKeyword(@Param("categoryId") Integer productCategoryId, @Param("keyword") String keyword, Pageable pageable);

}
