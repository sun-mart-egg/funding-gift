package com.d201.fundingift.funding.dto.response;

import com.d201.fundingift.funding.entity.Funding;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetFundingCalendarResponse {

    @Schema(description = "펀딩 고유번호", example = "55")
    private Long fundingId;

    @Schema(description = "기념일 날짜", example = "2024-12-12")
    private String anniversaryDate;

    @Schema(description = "펀딩시작 날짜", example = "2024-12-08")
    private String startDate;

    @Schema(description = "펀딩종료 날짜", example = "2024-12-14")
    private String endDate;

    @Schema(description = "펀딩명", example = "내생일이야")
    private String title; //펀딩명

    @Schema(description = "펀딩 상태", example = "IN_PROGRESS")
    private String fundingStatus;

    @Schema(description = "펀딩 생성자 고유번호", example = "43")
    private Long consumerId;

    @Schema(description = "펀딩 생성자명", example = "수빈")
    private String consumerName;

    @Schema(description = "기념일 고유번호", example = "1")
    private Integer anniversaryCategoryId;

    @Schema(description = "기념일명", example = "생일")
    private String anniversaryCategoryName;

    @Builder
    private GetFundingCalendarResponse(Long fundingId, String anniversaryDate, String startDate, String endDate, String title, String fundingStatus, Long consumerId, String consumerName, Integer anniversaryCategoryId, String anniversaryCategoryName) {
        this.fundingId = fundingId;
        this.anniversaryDate = anniversaryDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.fundingStatus = fundingStatus;
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.anniversaryCategoryId = anniversaryCategoryId;
        this.anniversaryCategoryName = anniversaryCategoryName;
    }

    public static GetFundingCalendarResponse from(Funding funding) {
        return GetFundingCalendarResponse.builder()
                .fundingId(funding.getId())
                .anniversaryDate(funding.getAnniversaryDateToString())
                .startDate(funding.getStartDateToString())
                .endDate(funding.getEndDateToString())
                .title(funding.getTitle())
                .fundingStatus(String.valueOf(funding.getFundingStatus()))
                .consumerId(funding.getConsumer().getId())
                .consumerName(funding.getConsumer().getName())
                .anniversaryCategoryId(funding.getAnniversaryCategory().getId())
                .anniversaryCategoryName(funding.getAnniversaryCategory().getName())
                .build();
    }
}
