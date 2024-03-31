package com.d201.fundingift.funding.controller;


import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.funding.dto.request.PostFundingRequest;
import com.d201.fundingift.funding.dto.response.GetFundingDetailResponse;
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

    @Operation(summary = "내가 만든 펀딩 목록 보기",
            description = """
                           `token` \n
                           내가 만든 펀딩 목록을 볼 수 있습니다. \n
                           제품명 keyword에 넣으면 검색 가능합니다. \n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("/my-fundings")
    public SuccessResponse<SliceList<GetFundingResponse>> getMyFundings(@Schema(description = "제품명으로 펀딩 목록 조회", example = "귀걸이") @RequestParam(required = false, name = "keyword") String keyword,
                                                                        @PageableDefault(size=4, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseUtils.ok(fundingService.getMyFundings(keyword, pageable), GET_MY_FUNDINGS_SUCCESS);
    }

    @Operation(summary = "친구가 만든 펀딩 목록 보기",
            description = """
                           `token` \n
                           친구가 만든 펀딩 목록을 볼 수 있습니다. \n
                           친구 아이디 friend-consumer-id에 필수로 넣어야 합니다. \n
                           제품명 keyword에 넣으면 검색 가능합니다. \n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 친구 아이디 존재 여부 / 보려는 펀딩 목록의 대상이 자신의 친구인지와 친한친구인지 여부",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("/friend-fundings")
    public SuccessResponse<SliceList<GetFundingResponse>> getFriendFundings(
                                                                        @Schema(description = "친구 아이디", example = "43")
                                                                        @RequestParam(required = true, name = "friend-consumer-id") Long friendConsumerId,
                                                                        @Schema(description = "제품명으로 펀딩 목록 조회", example = "귀걸이")
                                                                        @RequestParam(required = false, name = "keyword") String keyword,
                                                                        @PageableDefault(size=3, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseUtils.ok(fundingService.getFriendFundings(friendConsumerId, keyword, pageable), GET_FRIEND_FUNDINGS_SUCCESS);
    }

    @Operation(summary = "펀딩 스토리 보기",
            description = """
                           `token` \n
                           펀딩 스토리를 볼 수 있습니다. \n
                           진행중인 펀딩만 보여줍니다. \n
                           시작일 기준 오름차순 입니다. \n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 친구 아이디 존재 여부 / 보려는 펀딩 목록의 대상이 자신의 친구인지, 친한친구인지 여부",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("/story")
    public SuccessResponse<List<GetFundingResponse>> getFundingsStory(
            @Schema(description = "친구 아이디", example = "43")
            @RequestParam(name="consumer-id") Long consumerId
    ) {
        return ResponseUtils.ok(fundingService.getFundingsStory(consumerId), GET_FUNDINGS_STORY_SUCCESS);
    }

    @Operation(summary = "펀딩 상세 조회",
            description = """
                           `token` \n
                           펀딩 상세 조회입니다. \n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 보려는 펀딩 목록의 대상이 자신의 친구가 아닐 경우/ 펀딩 생성자의 친한 친구만 보도록 설정했을 때 그렇지 않은 경우",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("/detail/{funding-id}")
    public SuccessResponse<GetFundingDetailResponse> getFundingDetailResponse(@PathVariable(required = true, name = "funding-id") Long fundingId) {
        return ResponseUtils.ok(fundingService.getFundingDetailResponse(fundingId), GET_FUNDING_DETAIL_SUCCESS);
    }
}
