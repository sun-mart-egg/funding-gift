package com.d201.fundingift.product.repository;

import com.d201.fundingift.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findAll();
    boolean existsByIdAndDeletedAtIsNull(Integer categoryId);

}
