package com.d201.fundingift.consumer.controller;

import com.d201.fundingift._common.response.ApiResponseDto;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.consumer.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/consumers")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;

    // 특정 사용자 프로필 조회 (보안상 현재 인증된 사용자의 ID와 경로 변수의 ID를 비교하여 일치하는 경우에만 조회를 허용하는 로직 추가 필요)
    @GetMapping("/{consumerId}")
    public ApiResponseDto<?> getConsumerProfile(@PathVariable Long consumerId) {
        log.info("ConsumerController.getConsumerProfile");
        return ResponseUtils.ok(consumerService.getConsumerProfile(consumerId), SuccessType.LOGIN_SUCCESS); // 여기서 SuccessType은 상황에 맞게 조정
    }

    // 현재 인증된 사용자의 정보 조회
    @GetMapping
    public ApiResponseDto<?> getMyInfo(Authentication authentication) {
        log.info("ConsumerController.getMyInfo");
        return ResponseUtils.ok(consumerService.getMyInfo(authentication), SuccessType.LOGIN_SUCCESS);
    }
}
