package com.d201.fundingift.funding.dto.response;

import com.d201.fundingift.funding.entity.Funding;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetFundingDetailResponse {

    @Schema(description = "펀딩 고유번호", example = "55")
    private Long fundingId;

    @Schema(description = "모인 금액", example = "50000")
    private Integer sumPrice;

    @Schema(description = "최소 금액", example = "5000")
    private Integer minPrice;

    @Schema(description = "목표 금액(펀딩 만들 당시의 제품 금액)", example = "500000")
    private Integer targetPrice;

    @Schema(description = "기념일 날짜", example = "2024-12-12")
    private String anniversaryDate;

    @Schema(description = "펀딩시작 날짜", example = "2024-12-08")
    private String startDate;

    @Schema(description = "펀딩종료 날짜", example = "2024-12-14")
    private String endDate;

    @Schema(description = "펀딩명", example = "내생일이야")
    private String title; //펀딩명

    @Schema(description = "펀딩 내용", example = "다들 축하해줘.")
    private String content;

    @Schema(description = "펀딩 상태", example = "IN_PROGRESS")
    private String fundingStatus;

    @Schema(description = "공개 여부", example = "true")
    private Boolean isPrivate;

    @Schema(description = "펀딩 생성자 고유번호", example = "43")
    private Long consumerId;

    @Schema(description = "펀딩 생성자명", example = "수빈")
    private String consumerName;

    @Schema(description = "기념일 고유번호", example = "1")
    private Integer anniversaryCategoryId;

    @Schema(description = "기념일명", example = "생일")
    private String anniversaryCategoryName;

    @Schema(description = "제품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "제품명", example = "에어팟 맥스")
    private String productName;

    @Schema(description = "제품 이미지", example = "image url")
    private String productImage;

    @Schema(description = "제품옵션 고유번호", example = "5")
    private Long productOptionId;

    @Schema(description = "제품옵션명", example = "실버")
    private String productOptionName;

    @Builder
    private GetFundingDetailResponse(Long fundingId, Integer sumPrice, Integer minPrice, Integer targetPrice, String anniversaryDate, String startDate, String endDate, String title, String content, String fundingStatus, Boolean isPrivate, Long consumerId, String consumerName, Integer anniversaryCategoryId, String anniversaryCategoryName, Long productId, String productName, String productImage, Long productOptionId, String productOptionName) {
        this.fundingId = fundingId;
        this.sumPrice = sumPrice;
        this.minPrice = minPrice;
        this.targetPrice = targetPrice;
        this.anniversaryDate = anniversaryDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
        this.fundingStatus = fundingStatus;
        this.isPrivate = isPrivate;
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.anniversaryCategoryId = anniversaryCategoryId;
        this.anniversaryCategoryName = anniversaryCategoryName;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productOptionId = productOptionId;
        this.productOptionName = productOptionName;
    }

    public static GetFundingDetailResponse from(Funding funding) {
        return builder()
                .fundingId(funding.getId())
                .sumPrice(funding.getSumPrice())
                .minPrice(funding.getMinPrice())
                .targetPrice(funding.getTargetPrice())
                .anniversaryDate(funding.getAnniversaryDateToString())
                .startDate(funding.getStartDateToString())
                .endDate(funding.getEndDateToString())
                .title(funding.getTitle())
                .content(funding.getContent())
                .fundingStatus(String.valueOf(funding.getFundingStatus()))
                .isPrivate(funding.getIsPrivate())
                .consumerId(funding.getConsumer().getId())
                .consumerName(funding.getName())
                .anniversaryCategoryId(funding.getAnniversaryCategory().getId())
                .anniversaryCategoryName(funding.getAnniversaryCategory().getName())
                .productId(funding.getProduct().getId())
                .productName(funding.getProduct().getName())
                .productImage(funding.getProduct().getImage())
                .productOptionId(funding.getProductOption().getId())
                .productOptionName(funding.getProductOption().getName())
                .build();
    }
}
