package com.d201.fundingift.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class FcmNotificationDto {

    private String title;
    private String body;

    @Builder
    private FcmNotificationDto(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public static FcmNotificationDto of(String title, String body) {
        return builder()
                .title(title)
                .body(body)
                .build();
    }

}
