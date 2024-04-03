package com.d201.fundingift.fcmtoken.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.fcmtoken.dto.request.FcmTokenRequest;
import com.d201.fundingift.fcmtoken.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.d201.fundingift._common.response.SuccessType.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/fcm-tokens")
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping
    public SuccessResponse<Void> saveFcmToken(@RequestBody FcmTokenRequest request) {
        log.info("[NotificationController.saveFcmToken]");
        fcmTokenService.saveFcmToken(request);
        return ResponseUtils.ok(SAVE_FCM_TOKEN_SUCCESS);
    }

    @DeleteMapping
    public SuccessResponse<Void> deleteFcmToken(@RequestBody FcmTokenRequest request) {
        log.info("[NotificationController.deleteFcmToken]");
        fcmTokenService.deleteFcmToken(request);
        return ResponseUtils.ok(DELETE_FCM_TOKEN_SUCCESS);
    }

}
