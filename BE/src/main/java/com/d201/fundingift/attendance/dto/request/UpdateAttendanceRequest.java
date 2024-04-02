package com.d201.fundingift.attendance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "PostAttendanceRequest", description = "펀딩 참여자 감사 메시지 정보")
public class UpdateAttendanceRequest {

    @NotNull(message = "fundingId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "펀딩 ID", example = "1")
    private Long fundingId;

    @NotNull(message = "attendanceId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "펀딩참여 ID", example = "1")
    private Long attendanceId;

    @NotNull(message = "receiveMessage: 값이 null 이 아니어야 합니다.")
    @Schema(description = "받는 메시지", example = "고마워")
    private String receiveMessage;
}
