package com.d201.fundingift.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "PostPaymentInfoRequest", description = "결제 정보 저장 요청")
public class PostPaymentInfoRequest {

    @NotNull(message = "paymentInfoUid: 값이 null 이 아니어야 합니다.")
    @Schema(description = "결제정보 UUID", example = "UUID")
    private String paymentInfoUid; // 결제정보 UUID

    @NotNull(message = "attendanceId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "펀딩 참여 고유번호", example = "1")
    private Long attendanceId; // 펀딩 참여 고유번호
}
