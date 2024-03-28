package com.d201.fundingift.consumer.repository;

import com.d201.fundingift.consumer.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    Optional<Consumer> findBySocialIdAndDeletedAtIsNull(String socialId);
    Optional<Consumer> findByIdAndDeletedAtIsNull(Long id);
}
