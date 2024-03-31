package com.d201.fundingift.funding.controller;


import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.funding.dto.request.PostFundingRequest;
import com.d201.fundingift.funding.dto.response.GetFundingResponse;
import com.d201.fundingift.funding.service.FundingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.d201.fundingift._common.response.SuccessType.*;

@Tag(name = "fundings", description = "펀딩 관련 API")
@RestController
@RequestMapping("api/fundings")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @Operation(summary = "펀딩 생성",
            description = "소비자가 펀딩을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "소비자가 없는 경우 / 제품이 없는 경우 / 제품 옵션이 없는 경우 / 기념일 카테고리가 없는 경우 / 펀딩 기간이 7일 이상인 경우",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping
    public SuccessResponse<Void> postFunding(@RequestBody PostFundingRequest fundingCreateRequestDto) {

        fundingService.postFunding(fundingCreateRequestDto);
        return ResponseUtils.ok(CREATE_FUNDING_SUCCESS);
    }

    @GetMapping("/my-fundings")
    public SuccessResponse<SliceList<GetFundingResponse>> getMyFundings(@RequestParam(required = false, name = "keyword") String keyword,
                                                                        @PageableDefault(size=3, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseUtils.ok(fundingService.getMyFundings(keyword, pageable), GET_MY_FUNDINGS_SUCCESS);
    }

    @GetMapping("/friend-fundings")
    public SuccessResponse<SliceList<GetFundingResponse>> getFriendFundings(
                                                                        @RequestParam(required = true, name = "friend-consumer-id") Long friendConsumerId,
                                                                        @RequestParam(required = false, name = "keyword") String keyword,
                                                                        @PageableDefault(size=3, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseUtils.ok(fundingService.getFriendFundings(friendConsumerId, keyword, pageable), GET_FRIEND_FUNDINGS_SUCCESS);
    }

    @GetMapping("/story")
    public SuccessResponse<List<GetFundingResponse>> getFundingsStory(
            @RequestParam(name="consumer-id") Long consumerId
    ) {
        return ResponseUtils.ok(fundingService.getFundingsStory(consumerId), GET_FUNDINGS_STORY_SUCCESS);
    }
}
