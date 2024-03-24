package com.d201.fundingift.funding.controller;


import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.funding.dto.request.PostFundingRequest;
import com.d201.fundingift.funding.service.FundingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "fundings", description = "펀딩 관련 API")
@RestController
@RequestMapping("api/fundings")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @PostMapping
    public SuccessResponse<Void> postFunding(@RequestBody PostFundingRequest fundingCreateRequestDto) {

        fundingService.postFunding(fundingCreateRequestDto);
        return ResponseUtils.ok(SuccessType.CREATE_FUNDING_SUCCESS);
    }

}
