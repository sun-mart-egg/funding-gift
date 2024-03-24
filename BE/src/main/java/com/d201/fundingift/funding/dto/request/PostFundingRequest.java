package com.d201.fundingift.funding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(name = "FundingCreateRequestDto", description = "펀딩을 생성할 때 필요한 정보들 입니다.")
public class PostFundingRequest {

    @Schema(description = "최소 금액", example = "1000")
    private Integer minPrice;

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

    @Schema(description = "펀딩 내용", example = "다들 많은 참여 부탁해")
    private String content;

    @Schema(description = "환불 계좌은행", example = "농협")
    private String accountBank;

    @Schema(description = "환불 계좌번호", example = "3523333333383")
    private String accountNo;

    @Schema(description = "배송자명", example = "아무개")
    private String name; //배송자명

    @Schema(description = "배송자 전화번호", example = "01012342421")
    private String phoneNumber;

    @Schema(description = "기본 주소", example = "진평길79-1")
    private String defaultAddr; //기본 주소

    @Schema(description = "상세주소", example = "가을아침 1201호")
    private String detailAddr;

    @Schema(description = "우편번호", example = "53219")
    private String zipCode;

    @Schema(description = "공개 여부", example = "true")
    private Boolean isPrivate;

    @Schema(description = "기념일 종류", example = "1")
    private Integer anniversaryCategoryId;

    @Schema(description = "제품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "제품옵션 고유번호", example = "1")
    private Long productOptionId;

}
