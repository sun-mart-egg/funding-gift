package com.d201.fundingift.consumeralarm.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostConsumerAlarmRequest {
    private Long consumerId;
    private String message;
    private String messageType;  // Enum을 String으로 변환

    @Builder
    public PostConsumerAlarmRequest(Long consumerId, String message, String messageType) {
        this.consumerId = consumerId;
        this.message = message;
        this.messageType = messageType;
    }
}
