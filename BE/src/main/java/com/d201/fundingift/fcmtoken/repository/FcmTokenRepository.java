package com.d201.fundingift.fcmtoken.repository;

import com.d201.fundingift.fcmtoken.entity.FcmToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FcmTokenRepository extends CrudRepository<FcmToken, String> {

    Optional<FcmToken> findByConsumerIdAndFcmTokenValue(Long consumerId, String fcmTokenValue);
    Optional<FcmToken> findByConsumerId(Long consumerId);

}
