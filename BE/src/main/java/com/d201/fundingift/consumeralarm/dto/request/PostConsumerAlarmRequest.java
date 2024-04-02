package com.d201.fundingift.consumeralarm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostConsumerAlarmRequest {
    private Long consumerId;
    @NotBlank(message = "메세지는 비어 있을 수 없습니다.")
    @Size(max = 50, message = "메시지는 최대 50자까지 가능합니다.")
    private String message;
    private String messageType;  // Enum을 String으로 변환

    @Builder
    public PostConsumerAlarmRequest(Long consumerId, String message, String messageType) {
        this.consumerId = consumerId;
        this.message = message;
        this.messageType = messageType;
    }
}
