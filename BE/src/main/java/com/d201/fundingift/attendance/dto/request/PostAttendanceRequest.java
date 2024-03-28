package com.d201.fundingift.attendance.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostAttendanceRequest {

    @Size(min = 4, max = 20, message = "sendMessageTitle: 크기가 4에서 20 사이여야 합니다.")
    private String sendMessageTitle;

    private String sendMessage;

    @NotNull(message = "price: 값이 null 이 아니어야 합니다.")
    private Integer price;

    @NotNull(message = "consumerId: 값이 null 이 아니어야 합니다.")
    private Long consumerId;

    @NotNull(message = "fundingId: 값이 null 이 아니어야 합니다.")
    private Long fundingId;

    @NotNull(message = "paymentInfoId: 값이 null 이 아니어야 합니다.")
    private Long paymentInfoId;
}
