package com.d201.fundingift.attendance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "GetAttendanceDetailsRequest", description = "내 펀딩의 참여자 정보들을 조회할 때 필요합니다.")
public class GetAttendanceDetailsRequest {

    @NotNull(message = "fundingId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "펀딩 ID", example = "1")
    private Long fundingId;
}
