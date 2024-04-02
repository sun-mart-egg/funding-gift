package com.d201.fundingift.notification.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.notification.dto.request.FcmTokenRequest;
import com.d201.fundingift.notification.entity.FcmToken;
import com.d201.fundingift.notification.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.d201.fundingift._common.response.ErrorType.FCM_TOKEN_ALREADY_EXIST;
import static com.d201.fundingift._common.response.ErrorType.FCM_TOKEN_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final FcmTokenRepository fcmTokenRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void saveFcmToken(FcmTokenRequest request) {
        Long consumerId = securityUtil.getConsumerId();
        String fcmTokenValue = request.getFcmToken();

        // 저장된 토큰이 이미 존재하는 경우
        if (findByConsumerIdAndFcmTokenValue(consumerId, fcmTokenValue).isPresent()) {
            throw new CustomException(FCM_TOKEN_ALREADY_EXIST);
        }
        // 저장
        fcmTokenRepository.save(FcmToken.of(consumerId, fcmTokenValue));
    }

    @Transactional
    public void deleteFcmToken(FcmTokenRequest request) {
        Long consumerId = securityUtil.getConsumerId();
        String fcmTokenValue = request.getFcmToken();

        // 삭제
        fcmTokenRepository.delete(findByConsumerIdAndFcmTokenValue(consumerId, fcmTokenValue)
                .orElseThrow(() -> new CustomException(FCM_TOKEN_NOT_FOUND))); // 토큰이 저장되어 있지 않은 경우
    }

    private Optional<FcmToken> findByConsumerIdAndFcmTokenValue(Long consumerId, String fcmTokenValue) {
        return fcmTokenRepository.findByConsumerIdAndFcmTokenValue(consumerId, fcmTokenValue);
    }

}
