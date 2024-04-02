package com.d201.fundingift.notification.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash(value = "fcmtoken")
public class FcmToken implements Serializable {

    @Id
    private String fcmTokenId;

    @Indexed
    private Long consumerId;

    @Indexed
    private String fcmTokenValue;

    @Builder
    private FcmToken(Long consumerId, String fcmTokenValue) {
        this.consumerId = consumerId;
        this.fcmTokenValue = fcmTokenValue;
    }

    public static FcmToken of(Long consumerId, String fcmTokenValue) {
        return builder()
                .consumerId(consumerId)
                .fcmTokenValue(fcmTokenValue)
                .build();
    }

}

