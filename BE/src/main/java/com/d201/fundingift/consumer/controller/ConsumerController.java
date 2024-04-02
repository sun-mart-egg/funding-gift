package com.d201.fundingift.consumer.controller;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.dto.request.PutConsumerInfoRequestDto;
import com.d201.fundingift.consumer.dto.response.GetConsumerInfoByIdResponse;
import com.d201.fundingift.consumer.dto.response.GetConsumerMyInfoResponse;
import com.d201.fundingift.consumer.service.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    private final SecurityUtil securityUtil;

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
    @GetMapping("/{consumer-id}")
    public SuccessResponse<GetConsumerInfoByIdResponse> getConsumerProfile(@PathVariable("consumer-id") Long consumerId) {
        log.info("ConsumerController.getConsumerProfile");
        return ResponseUtils.ok(consumerService.getConsumerInfoById(consumerId), SuccessType.GET_CONSUMER_INFO_SUCCESS); // 여기서 SuccessType은 상황에 맞게 조정
    }

    @Operation(summary = "소비자 내 정보 수정",
            description = """
                    내 정보를 수정합니다. `Token` \n
                    ex)\n
                    name은 1자 이상 10자 이하이어야 합니다. \n
                    email은 유효한 이메일 형식이어야 하며 최대 50자까지 가능합니다. \n
                    phoneNumber는 10자리 또는 11자리 숫자여야 합니다.\n
                    birthyear와 birthday는 정확히 4자리 숫자여야 합니다.\n
                    gender는 'male' 또는 'female'만 가능합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true)
    })
    @PutMapping
    public SuccessResponse<Void> updateConsumerInfo(@Valid @RequestBody PutConsumerInfoRequestDto putConsumerInfoRequestDto) {
        consumerService.updateConsumerInfo(putConsumerInfoRequestDto);
        return ResponseUtils.ok(SuccessType.PUT_CONSUMER_ADDITION_INFO_SUCCESS);
    }

    @Operation(summary = "소비자 로그아웃",
            description = "Redis의 저장된 토큰을 모두 삭제하여 로그아웃 합니다. `Token`"
    )
    // 로그아웃
    @PostMapping("/logout")
    public SuccessResponse<Void> postLogoutUser() {
        log.info("ConsumerController.postLogoutUser");
        consumerService.logoutUser();
        // 로그아웃 성공 응답 반환
        return ResponseUtils.ok(SuccessType.LOGOUT_SUCCESS);
    }

    @GetMapping("/{consumer-id}/in-progress-funding")
    @Operation(summary = "진행 중인 펀딩 확인", description = "사용자가 진행 중이거나 참여 중인 펀딩이 있는지 확인합니다.")
    public SuccessResponse<Boolean> isConsumerInProgressOrAttendanceFunding(@PathVariable("consumer-id") Long consumerId) {
        boolean hasInProgressFunding = consumerService.isConsumerInProgressOrAttendanceFunding(consumerId);
        return ResponseUtils.ok(hasInProgressFunding,SuccessType.CHECK_CONSUMER_IN_PROGRESS_FUNDING);
    }
}
