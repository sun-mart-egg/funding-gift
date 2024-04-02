package com.d201.fundingift.notification.service;

import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.notification.dto.request.PostFcmTokenRequest;
import com.d201.fundingift.notification.entity.FcmToken;
import com.d201.fundingift.notification.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.d201.fundingift._common.response.SuccessType.SAVE_FCM_TOKEN_ALREADY_DONE;
import static com.d201.fundingift._common.response.SuccessType.SAVE_FCM_TOKEN_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FcmTokenRepository fcmTokenRepository;
    private final SecurityUtil securityUtil;

    public SuccessType saveFcmToken(PostFcmTokenRequest request) {
        Long consumerId = securityUtil.getConsumerId();
        String fcmTokenValue = request.getFcmToken();

        // 이미 저장된 토큰이 존재하면
        if (isPresentByConsumerIdAndFcmTokenValue(consumerId, fcmTokenValue)) {
            return SAVE_FCM_TOKEN_ALREADY_DONE;
        }

        fcmTokenRepository.save(FcmToken.of(consumerId, fcmTokenValue));
        return SAVE_FCM_TOKEN_SUCCESS;
    }

    private boolean isPresentByConsumerIdAndFcmTokenValue(Long consumerId, String fcmTokenValue) {
        return fcmTokenRepository.findByConsumerIdAndFcmTokenValue(consumerId, fcmTokenValue).isPresent();
    }

}
