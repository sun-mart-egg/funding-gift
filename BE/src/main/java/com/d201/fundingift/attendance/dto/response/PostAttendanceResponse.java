package com.d201.fundingift.attendance.dto.response;

import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.funding.entity.Funding;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostAttendanceResponse {

    @Schema(description = "펀딩 참여 고유번호", example = "1")
    private Long attendanceId;

    @Schema(description = "{펀딩생성자이름}님의 {제품명} 펀딩", example = "대영님의 제품명 펀딩")
    private String fundingName;

    @Schema(description = "펀딩 참여 금액", example = "50000")
    private Integer price;

    @Schema(description = "펀딩 참여자 고유번호", example = "150")
    private Long attendeeId;

    @Schema(description = "펀딩 참여자 이름", example = "대영")
    private String attendeeName;

    @Schema(description = "펀딩 참여자 이메일", example = "asdf@naver.com")
    private String email;

    @Builder
    private PostAttendanceResponse(Long attendanceId, String fundingName, Integer price, Long attendeeId, String attendeeName, String email) {
        this.attendanceId = attendanceId;
        this.fundingName = fundingName;
        this.price = price;
        this.attendeeId = attendeeId;
        this.attendeeName = attendeeName;
        this.email = email;
    }

    public static PostAttendanceResponse from(Attendance attendance, Consumer attendee, Funding funding) {
        return builder()
                .attendanceId(attendance.getId())
                .fundingName(funding.getConsumer().getName() +"님의 " + funding.getProduct().getName() + " 펀딩")
                .price(attendance.getPrice())
                .attendeeId(attendee.getId())
                .attendeeName(attendee.getName())
                .email(attendee.getEmail())
                .build();
    }
}
