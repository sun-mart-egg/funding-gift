package com.d201.fundingift.funding.repository;

import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.entity.status.FundingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    List<Funding> findAllByConsumerIdAndFundingStatusOrderByStartDateAsc(Long consumerId, FundingStatus fundingStatus);

    List<Funding> findAllByConsumerIdAndFundingStatusAndIsPrivateOrderByStartDateAsc(Long consumerId, FundingStatus fundingStatus, Boolean isPrivate);

    @Query("select f from Funding f " +
            "where f.consumer.id = :consumerId and f.fundingStatus = 'IN_PROGRESS' and f.deletedAt is null ORDER BY f.startDate ASC Limit 1")
    Optional<Funding> findOneByConsumerIdAndFundingStatusAndDeletedAtIsNullOrderByStartDateAsc(Long consumerId);

    Optional<Funding> findByIdAndDeletedAtIsNull(Long fundingId);

    @Query("SELECT f FROM Funding f " +
            "WHERE " +
            "(YEAR(f.anniversaryDate) = :year AND MONTH(f.anniversaryDate) = :month) " +
            "AND f.consumer.id = :consumerId AND f.deletedAt IS NULL")
    List<Funding> findAllByConsumerIdAndDeletedAtIsNull(@Param("consumerId") Long consuerId, @Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT f FROM Funding f " +
            "WHERE " +
            "(YEAR(f.anniversaryDate) = :year AND MONTH(f.anniversaryDate) = :month) " +
            "AND f.consumer.id = :consumerId AND f.isPrivate = false AND f.deletedAt IS NULL")
    List<Funding> findAllByConsumerIdAndIsPrivateAndDeletedAtIsNull(@Param("consumerId") Long consuerId, @Param("year") Integer year, @Param("month") Integer month);
}
