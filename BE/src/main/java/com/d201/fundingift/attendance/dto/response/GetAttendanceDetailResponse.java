package com.d201.fundingift.attendance.dto.response;

import com.d201.fundingift.attendance.entity.Attendance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAttendanceDetailResponse {

    @Schema(description = "펀딩 참여 고유번호", example = "1")
    private Long attendanceId;

    @Schema(description = "보내는 메세지 제목", example = "축하해")
    private String sendMessageTitle;

    @Schema(description = "보내는 메세지 내용", example = "겁나 축하해")
    private String sendMessage;

    @Schema(description = "받는 메시지", example = "고마워")
    private String receiveMessage;

    @Schema(description = "펀딩 참여 금액", example = "50000")
    private Integer price;

    @Schema(description = "펀딩 참여자 고유번호", example = "150")
    private Long consumerId;

    @Schema(description = "펀딩 참여자 이름", example = "대영")
    private String consumerName;

    @Builder
    private GetAttendanceDetailResponse(Long attendanceId, String sendMessageTitle, String sendMessage, String receiveMessage, Integer price, Long consumerId, String consumerName) {
        this.attendanceId = attendanceId;
        this.sendMessageTitle = sendMessageTitle;
        this.sendMessage = sendMessage;
        this.receiveMessage = receiveMessage;
        this.price = price;
        this.consumerId = consumerId;
        this.consumerName = consumerName;
    }

    public static GetAttendanceDetailResponse from(Attendance attendance) {
        return GetAttendanceDetailResponse.builder()
                .attendanceId(attendance.getId())
                .sendMessageTitle(attendance.getSendMessageTitle())
                .sendMessage(attendance.getSendMessage())
                .receiveMessage(attendance.getReceiveMessage())
                .price(attendance.getPrice())
                .consumerId(attendance.getConsumer().getId())
                .consumerName(attendance.getConsumer().getName())
                .build();
    }
}
