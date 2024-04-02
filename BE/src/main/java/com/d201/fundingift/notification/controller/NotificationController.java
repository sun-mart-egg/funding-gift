package com.d201.fundingift.notification.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.notification.dto.request.PostFcmTokenRequest;
import com.d201.fundingift.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.d201.fundingift._common.response.SuccessType.SAVE_FCM_TOKEN_SUCCESS;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public SuccessResponse<Void> saveFcmToken(@RequestBody PostFcmTokenRequest request) {
        log.info("[NotificationController.saveFcmToken]");
        return ResponseUtils.ok(notificationService.saveFcmToken(request));
    }

}
