package com.d201.fundingift.consumeralarm.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash(value = "consumer_alarm")
public class ConsumerAlarm implements Serializable {

    @Id
    private Long consumerAlarmId;
    @Indexed
    private Long consumerId;
    private String message;
    private String messageType; // ENUM ?
    private Boolean isRead;

    // 생성 시간, 수정 시간, 삭제 시간 구현 필요

    @Builder
    private ConsumerAlarm(Long consumerId, String message, String messageType, Boolean isRead) {
        this.consumerId = consumerId;
        this.message = message;
        this.messageType = messageType;
        this.isRead = isRead;
    }

}