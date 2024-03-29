package com.d201.fundingift.payment.repository;

import com.d201.fundingift.payment.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
}
