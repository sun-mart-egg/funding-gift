package com.d201.fundingift.notification.service;

import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.notification.dto.request.PostFcmTokenRequest;
import com.d201.fundingift.notification.entity.FcmToken;
import com.d201.fundingift.notification.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FcmTokenRepository fcmTokenRepository;
    private final SecurityUtil securityUtil;

    public void saveFcmToken(PostFcmTokenRequest request) {
        Long consumerId = securityUtil.getConsumerId();
        fcmTokenRepository.save(FcmToken.of(consumerId, request.getFcmToken()));
    }

}
