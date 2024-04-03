package com.d201.fundingift.payment.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.payment.entity.status.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE payment_info set deleted_at = DATE_ADD(NOW(), INTERVAL 9 HOUR) where payment_info_id = ?")
public class PaymentInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id", nullable = false)
    private Long id;

    private String paymentInfoUid; // 결제정보 UUID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus; // NOT_PAID, PAID, DELETED

    private Integer price;

    @Builder
    private PaymentInfo(Long id, String paymentInfoUid, PaymentStatus paymentStatus, Integer price) {
        this.id = id;
        this.paymentInfoUid = paymentInfoUid;
        this.paymentStatus = paymentStatus;
        this.price = price;
    }

    public static PaymentInfo of(String paymentInfoUid, PaymentStatus paymentStatus, Integer price) {
        return builder()
                .paymentInfoUid(paymentInfoUid)
                .paymentStatus(paymentStatus)
                .price(price)
                .build();
    }
}
