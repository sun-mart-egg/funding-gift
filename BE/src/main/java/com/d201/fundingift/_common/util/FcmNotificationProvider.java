package com.d201.fundingift._common.util;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.dto.FcmNotificationDto;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.fcmtoken.repository.FcmTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.d201.fundingift._common.response.ErrorType.FCM_NOTIFICATION_SEND_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmNotificationProvider {

    private final FcmTokenRepository fcmTokenRepository;

    public void sendToOne(Long consumerId, FcmNotificationDto fcmNotificationDto) {
        send(consumerId, fcmNotificationDto);
    }

    public void sendToMany(List<Consumer> consumers, FcmNotificationDto fcmNotificationDto) {
        for (Consumer consumer : consumers) {
            if (consumer == null) {
                continue;
            }
            log.info("send to: {}", consumer.getId());
            send(consumer.getId(), fcmNotificationDto);
        }
    }

    private void send(Long consumerId, FcmNotificationDto fcmNotificationDto) {
        fcmTokenRepository.findAllByConsumerId(consumerId).iterator()
                .forEachRemaining(t -> sendMessage(buildMessage(t.getFcmTokenValue(), fcmNotificationDto)));
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
