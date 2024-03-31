package com.d201.fundingift.funding.dto.response;

import com.d201.fundingift.funding.entity.Funding;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetFundingResponse {

    @Schema(description = "펀딩 고유번호", example = "55")
    private Long fundingId;

    @Schema(description = "목표 금액(펀딩 만들 당시의 제품 금액)", example = "500000")
    private Integer targetPrice;

    @Schema(description = "모인 금액", example = "50000")
    private Integer sumPrice;

    @Schema(description = "기념일 날짜", example = "2024-12-12")
    private String anniversaryDate;

    @Schema(description = "펀딩시작 날짜", example = "2024-12-08")
    private String startDate;

    @Schema(description = "펀딩종료 날짜", example = "2024-12-14")
    private String endDate;

    @Schema(description = "펀딩명", example = "내생일이야")
    private String title; //펀딩명

    @Schema(description = "공개 여부", example = "true")
    private Boolean isPrivate;

    @Schema(description = "기념일 종류", example = "1")
    private Integer anniversaryCategoryId;

    @Schema(description = "제품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "제품명", example = "에어팟 맥스")
    private String productName;

    @Schema(description = "제품 이미지", example = "image url")
    private String productImage;

    @Schema(description = "펀딩 상태", example = "IN_PROGRESS")
    private String fundingStatus;

    @Builder
    private GetFundingResponse(Long fundingId, Integer targetPrice, Integer sumPrice, String anniversaryDate, String startDate, String endDate, String title, Boolean isPrivate, Integer anniversaryCategoryId, Long productId, String productName, String productImage, String fundingStatus) {
        this.fundingId = fundingId;
        this.targetPrice = targetPrice;
        this.sumPrice = sumPrice;
        this.anniversaryDate = anniversaryDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.isPrivate = isPrivate;
        this.anniversaryCategoryId = anniversaryCategoryId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.fundingStatus = fundingStatus;
    }

    public static GetFundingResponse from(Funding funding) {
        return builder()
                .fundingId(funding.getId())
                .targetPrice(funding.getTargetPrice())
                .sumPrice(funding.getSumPrice())
                .anniversaryDate(funding.getAnniversaryDateToString())
                .startDate(funding.getStartDateToString())
                .endDate(funding.getEndDateToString())
                .title(funding.getTitle())
                .isPrivate(funding.getIsPrivate())
                .anniversaryCategoryId(funding.getAnniversaryCategory().getId())
                .productId(funding.getProduct().getId())
                .productName(funding.getProduct().getName())
                .productImage(funding.getProduct().getImage())
                .fundingStatus(funding.getFundingStatus().name())
                .build();
    }
}
