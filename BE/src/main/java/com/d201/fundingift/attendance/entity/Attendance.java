package com.d201.fundingift.attendance.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.funding.entity.Funding;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE attendance set deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') where attendance_id = ?")
public class Attendance extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String sendMessageTitle;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String sendMessage;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String receiveMessage;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", referencedColumnName = "funding_id")
    private Funding funding;

    @Builder
    private Attendance(String sendMessageTitle, String sendMessage, String receiveMessage, Integer price, Consumer consumer, Funding funding) {
        this.sendMessageTitle = sendMessageTitle;
        this.sendMessage = sendMessage;
        this.receiveMessage = receiveMessage;
        this.price = price;
        this.consumer = consumer;
        this.funding = funding;
    }

    public static Attendance from(PostAttendanceRequest postAttendanceRequest, Consumer consumer, Funding funding) {
        return Attendance.builder()
                .sendMessageTitle(postAttendanceRequest.getSendMessageTitle())
                .sendMessage(postAttendanceRequest.getSendMessage())
                .price(postAttendanceRequest.getPrice())
                .consumer(consumer)
                .funding(funding)
                .build();
    }


}
