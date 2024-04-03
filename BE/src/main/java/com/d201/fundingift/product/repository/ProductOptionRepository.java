package com.d201.fundingift.product.repository;

import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("select po from ProductOption po " +
            "where po.id = :productOptionId and po.status = 'ACTIVE' and po.deletedAt is null")
    Optional<ProductOption> findByIdAndStatusIsActive(@Param("productOptionId") Long productOptionId);

    @Query("select po from ProductOption po " +
            "where po.id = :productOptionId and po.status <> 'INACTIVE' and po.deletedAt is null")
    Optional<ProductOption> findByIdAndStatusIsNotInactive(@Param("productOptionId") Long productOptionId);

    @Query("select po from ProductOption po " +
            "where po.product = :product and po.status <> 'INACTIVE' and po.deletedAt is null")
    List<ProductOption> findAllByProduct(@Param("product") Product product);

}
