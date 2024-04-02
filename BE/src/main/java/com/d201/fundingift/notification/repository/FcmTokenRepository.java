package com.d201.fundingift.notification.repository;

import com.d201.fundingift.notification.entity.FcmToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FcmTokenRepository extends CrudRepository<FcmToken, String> {

    Optional<FcmToken> findByConsumerIdAndFcmTokenValue(Long consumerId, String fcmTokenValue);

}
