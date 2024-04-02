package com.d201.fundingift._common.util;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift.notification.dto.FcmNotificationDto;
import com.d201.fundingift.notification.repository.FcmTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.d201.fundingift._common.response.ErrorType.FCM_NOTIFICATION_SEND_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmNotificationProvider {

    private final FcmTokenRepository fcmTokenRepository;

    public void send(Long consumerId, FcmNotificationDto fcmNotificationDto) {
        fcmTokenRepository.findByConsumerId(consumerId).ifPresent(
                t -> sendMessage(buildMessage(t.getFcmTokenValue(), fcmNotificationDto)));
    }

    private Message buildMessage(String fcmToken, FcmNotificationDto fcmNotificationDto) {
        return Message.builder()
                .setToken(fcmToken)
                .setNotification(
                        Notification.builder()
                                .setTitle(fcmNotificationDto.getTitle())
                                .setBody(fcmNotificationDto.getBody())
                                .build())
                .build();
    }

    private void sendMessage(Message message) {
        try {
            FirebaseMessaging.getInstance().send(message);
            log.info("message send success");
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
            throw new CustomException(FCM_NOTIFICATION_SEND_FAILED);
        }
    }

}
