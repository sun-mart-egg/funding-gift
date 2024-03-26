package com.d201.fundingift.funding.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetFundingResponse {

    @Schema(description = "목표 금액(펀딩 만들 당시의 제품 금액)", example = "500000")
    private Integer targetPrice;

    @Schema(description = "기념일 날짜", example = "2024-12-12")
    private LocalDate anniversaryDate;

    @Schema(description = "펀딩시작 날짜", example = "2024-12-08")
    private LocalDate startDate;

    @Schema(description = "펀딩종료 날짜", example = "2024-12-14")
    private LocalDate endDate;

    @Schema(description = "펀딩명", example = "내생일이야")
    private String title; //펀딩명

    @Schema(description = "공개 여부", example = "true")
    private Boolean isPrivate;

    @Schema(description = "기념일 종류", example = "1")
    private Integer anniversaryCategoryId;

    @Schema(description = "제품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "제품옵션 고유번호", example = "1")
    private Long productOptionId;
}
