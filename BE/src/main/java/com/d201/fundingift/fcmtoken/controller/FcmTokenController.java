package com.d201.fundingift.fcmtoken.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.fcmtoken.dto.request.FcmTokenRequest;
import com.d201.fundingift.fcmtoken.service.FcmTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.d201.fundingift._common.response.SuccessType.*;

@Tag(name = "fcm-tokens", description = "FCM 토큰 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/fcm-tokens")
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @Operation(summary = "fcm token 저장",
            description = """
                           `token` \n
                           fcm token을 저장합니다. \n
                           로그인 할 때마다 토큰 발급 받아서 요청해주세요.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "토큰이 없는 경우 / 이미 저장이 되어있는 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping
    public SuccessResponse<Void> saveFcmToken(@RequestBody FcmTokenRequest request) {
        log.info("[NotificationController.saveFcmToken]");
        fcmTokenService.saveFcmToken(request);
        return ResponseUtils.ok(SAVE_FCM_TOKEN_SUCCESS);
    }

    @Operation(summary = "fcm token 삭제",
            description = """
                           `token` \n
                           fcm token을 삭제합니다. \n
                           로그아웃 할 때마다 요청해주세요.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "토큰이 없는 경우 / 해당 토큰이 없는 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping
    public SuccessResponse<Void> deleteFcmToken(@RequestBody FcmTokenRequest request) {
        log.info("[NotificationController.deleteFcmToken]");
        fcmTokenService.deleteFcmToken(request);
        return ResponseUtils.ok(DELETE_FCM_TOKEN_SUCCESS);
    }

}
