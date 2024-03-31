package com.d201.fundingift.funding.repository;

import com.d201.fundingift.funding.entity.Funding;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FundingRepository extends JpaRepository<Funding, Long> {

    @Query("select f from Funding f " +
            "where f.consumer.id = :consumerId and f.deletedAt is null")
    Slice<Funding> findAllByConsumerIdAndDeletedAtIsNull(@Param("consumerId") Long consumerId, Pageable pageable);

    @Query("select f from Funding f " +
            "where f.consumer.id = :consumerId and f.isPrivate = false and f.deletedAt is null")
    Slice<Funding> findAllByConsumerIdAndIsPrivateAndDeletedAtIsNull(@Param("consumerId") Long consumerId, Pageable pageable);

    @Query("select f from Funding f " +
            "where f.consumer.id = :consumerId and f.product.name like %:keyword% and f.deletedAt is null")
    Slice<Funding> findAllByConsumerIdAndProductNameAndDeletedAtIsNull(@Param("consumerId") Long consumerId, @Param("keyword") String keyword, Pageable pageable);

    @Query("select f from Funding f " +
            "where f.consumer.id = :consumerId and f.isPrivate = false and f.product.name like %:keyword% and f.deletedAt is null")
    Slice<Funding> findAllByConsumerIdAndIsPrivateAndProductNameAndDeletedAtIsNull(@Param("consumerId") Long consumerId, @Param("keyword") String keyword, Pageable pageable);

    Optional<Funding> findByIdAndDeletedAtIsNull(Long fundingId);
}
