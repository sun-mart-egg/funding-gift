package com.d201.fundingift.address.repository;

import com.d201.fundingift.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByConsumerId(Long consumerId);
}