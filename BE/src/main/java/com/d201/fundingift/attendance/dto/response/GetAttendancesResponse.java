package com.d201.fundingift.attendance.dto.response;

import com.d201.fundingift.attendance.entity.Attendance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAttendancesResponse {

    @Schema(description = "펀딩 참여 고유번호", example = "1")
    private Long attendanceId;

    @Schema(description = "보내는 메세지 제목", example = "축하해")
    private String sendMessageTitle;

    @Schema(description = "펀딩 참여자 고유번호", example = "150")
    private Long consumerId;

    @Schema(description = "펀딩 참여자 이름", example = "대영")
    private String consumerName;

    @Builder
    private GetAttendancesResponse(Long attendanceId, String sendMessageTitle, Long consumerId, String consumerName) {
        this.attendanceId = attendanceId;
        this.sendMessageTitle = sendMessageTitle;
        this.consumerId = consumerId;
        this.consumerName = consumerName;
    }

    public static GetAttendancesResponse from(Attendance attendance) {
        return GetAttendancesResponse.builder()
                .attendanceId(attendance.getId())
                .sendMessageTitle(attendance.getSendMessageTitle())
                .consumerId(attendance.getConsumer().getId())
                .consumerName(attendance.getConsumer().getName())
                .build();
    }
}
