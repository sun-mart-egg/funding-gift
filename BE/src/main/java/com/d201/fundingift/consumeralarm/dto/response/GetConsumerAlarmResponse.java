package com.d201.fundingift.consumeralarm.dto.response;

import com.d201.fundingift.consumeralarm.entity.ConsumerAlarm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class GetConsumerAlarmResponse {
    private Long consumerAlarmId;
    private Long consumerId;
    private String message;
    private String messageType; // Enum을 String으로 변환
    private Boolean isRead;
    private LocalDateTime createdDate;
    private LocalDateTime readTime;

    @Builder
    public GetConsumerAlarmResponse(Long consumerAlarmId, Long consumerId, String message,
                                    String messageType, Boolean isRead,
                                    LocalDateTime createdDate, LocalDateTime readTime) {
        this.consumerAlarmId = consumerAlarmId;
        this.consumerId = consumerId;
        this.message = message;
        this.messageType = messageType;
        this.isRead = isRead;
        this.createdDate = createdDate;
        this.readTime = readTime;
    }

    public static GetConsumerAlarmResponse from(ConsumerAlarm alarm) {
        return GetConsumerAlarmResponse.builder()
                .consumerAlarmId(alarm.getConsumerAlarmId())
                .consumerId(alarm.getConsumerId())
                .message(alarm.getMessage())
                .messageType(alarm.getMessageType().toString())
                .isRead(alarm.getIsRead())
                .createdDate(alarm.getCreatedDate())
                .readTime(alarm.getReadTime())
                .build();
    }
}
