package com.d201.fundingift.funding.repository;

import com.d201.fundingift.funding.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Long> {
}
