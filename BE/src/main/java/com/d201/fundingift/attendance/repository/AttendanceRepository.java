package com.d201.fundingift.attendance.repository;

import com.d201.fundingift.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
