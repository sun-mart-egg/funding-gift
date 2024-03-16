package com.d201.fundingift.payment.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.attendance.entity.Attendance;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE payment_info set deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') where payment_info_id = ?")
public class PaymentInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id", nullable = false)
    private Long id;

}
