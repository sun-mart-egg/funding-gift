package com.d201.fundingift.funding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(name = "DeleteFundingRequest", description = "펀딩을 삭제할 때 필요한 정보들 입니다.")
public class DeleteFundingRequest {

    @NotBlank(message = ":fundingId이 null이거나 빈칸이면 안됩니다.")
    @Schema(description = "펀딩 고유번호", example = "1")
    private Long fundingId;
}
