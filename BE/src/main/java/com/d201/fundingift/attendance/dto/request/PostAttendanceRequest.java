package com.d201.fundingift.attendance.dto.request;

import lombok.Getter;

@Getter
public class PostAttendanceRequest {

    private String sendMessageTitle;

    private String sendMessage;

    private Integer price;

    private Long consumerId;

    private Long fundingId;

    private Long paymentInfoId;
}
