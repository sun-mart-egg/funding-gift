package com.d201.fundingift.attendance.repository;

import com.d201.fundingift.attendance.entity.Attendance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("select a from Attendance a " +
            "where a.funding.id = :fundingId and a.deletedAt is null")
    Slice<Attendance> findAllByFundingIdAndDeletedAtIsNull(@Param("fundingId") Long fundingId, Pageable pageable);

    List<Attendance> findByConsumerIdAndDeletedAtIsNull(Long consumerId);
}
