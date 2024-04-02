package com.d201.fundingift.attendance.repository;

import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.funding.entity.Funding;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByIdAndDeletedAtIsNull(Long id);

    @Query("select a from Attendance a " +
            "where a.funding.id = :fundingId and a.deletedAt is null")
    Slice<Attendance> findAllByFundingIdAndDeletedAtIsNull(@Param("fundingId") Long fundingId, Pageable pageable);

    @Query("select f from Attendance a right join  a.funding f " +
            "where a.consumer.id = :consumerId and a.deletedAt is null")
    Slice<Funding> findAllByConsumerIdAndAndDeletedAtIsNull(@Param("consumerId") Long consumerId, Pageable pageable);
}
