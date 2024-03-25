package com.d201.fundingift.consumer.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.consumer.dto.response.GetConsumerInfoByIdResponse;
import com.d201.fundingift.consumer.dto.response.GetConsumerMyInfoResponse;
import com.d201.fundingift.consumer.service.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "consumers", description = "소비자 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/consumers")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ConsumerController {

    private final ConsumerService consumerService;

    // 현재 인증된 사용자의 정보 조회
    @Operation(summary = "소비자 내 정보 조회",
            description = "내 정보를 조회합니다. `Token`"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true)
    })
    @GetMapping
    public SuccessResponse<GetConsumerMyInfoResponse> getMyInfo(Authentication authentication) {
        log.info("ConsumerController.getMyInfo");
        return ResponseUtils.ok(consumerService.getConsumerMyInfo(authentication), SuccessType.GET_CONSUMER_INFO_SUCCESS);
    }

    @Operation(summary = "소비자 ID 조회",
            description = "소비자의 정보를 ID를 통해 조회합니다. `Token`"
   )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true)
    })
    // 특정 사용자 프로필 조회 (보안상 현재 인증된 사용자의 ID와 경로 변수의 ID를 비교하여 일치하는 경우에만 조회를 허용하는 로직 추가 필요)
    @GetMapping("/{consumer-id}")
    public SuccessResponse<GetConsumerInfoByIdResponse> getConsumerProfile(@PathVariable("consumer-id") Long consumerId) {
        log.info("ConsumerController.getConsumerProfile");
        return ResponseUtils.ok(consumerService.getConsumerInfoById(consumerId), SuccessType.GET_CONSUMER_INFO_SUCCESS); // 여기서 SuccessType은 상황에 맞게 조정
    }

}
