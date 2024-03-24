package com.d201.fundingift.product.repository;

import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("select po from ProductOption po " +
            "where po.product = :product and po.status <> 'INACTIVE' and po.deletedAt is null")
    List<ProductOption> findAllByProduct(@Param("product") Product product);

}
