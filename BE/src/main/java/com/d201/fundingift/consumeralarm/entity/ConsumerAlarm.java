package com.d201.fundingift.consumeralarm.entity;

import com.d201.fundingift.consumeralarm.dto.request.PostConsumerAlarmRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RedisHash(value = "consumer_alarm")
public class ConsumerAlarm implements Serializable {

    @Id
    private String consumerAlarmId;

    @Indexed
    private Long consumerId;

    private String message;

    private MessageType  messageType;

    private Boolean isRead;

    private LocalDateTime createdDate;

    private LocalDateTime readTime;

    @Builder
    private ConsumerAlarm(Long consumerId, String message, MessageType messageType, Boolean isRead, LocalDateTime createdDate) {
        this.consumerId = consumerId;
        this.message = message;
        this.messageType = messageType;
        this.isRead = isRead;
        this.createdDate = createdDate != null ? createdDate : LocalDateTime.now();
        this.readTime = null;
    }

    public static ConsumerAlarm from(PostConsumerAlarmRequest request) {
        return ConsumerAlarm.builder()
                .consumerId(request.getConsumerId())
                .message(request.getMessage())
                .messageType(MessageType.valueOf(request.getMessageType()))
                .isRead(false)
                .createdDate(LocalDateTime.now()) // 메시지 생성 시간 설정
                .build();
    }

    public enum MessageType {
        펀딩, 친구, 시스템
    }

    public void setIsRead() {
        this.isRead = true;
        this.readTime = LocalDateTime.now(); // 메시지를 읽은 시간으로 설정
    }
}