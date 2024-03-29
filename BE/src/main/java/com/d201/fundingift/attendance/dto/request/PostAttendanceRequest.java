package com.d201.fundingift.attendance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "PostAttendanceRequest", description = "펀딩 참여 요청")
public class PostAttendanceRequest {

    @Size(min = 4, max = 20, message = "sendMessageTitle: 크기가 4에서 20 사이여야 합니다.")
    @Schema(description = "펀딩 참여 축하 메시지 제목", example = "수빈아 생일 축하포카리스웨트")
    private String sendMessageTitle;

    @Schema(description = "펀딩 참여 축하 메시지", example = "수빈아 생일 축하포카리스웨트! 행복한 생일 보내고 내가 펀딩한 돈으로 좋은거 사.")
    private String sendMessage;

    @NotNull(message = "price: 값이 null 이 아니어야 합니다.")
    @Schema(description = "펀딩 금액", example = "100000")
    private Integer price;

    @NotNull(message = "fundingId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "펀딩 ID", example = "1")
    private Long fundingId;
}
