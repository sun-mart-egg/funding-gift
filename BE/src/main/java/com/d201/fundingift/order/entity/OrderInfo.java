package com.d201.fundingift.order.entity;

import com.d201.fundingift._common.entity.BaseTime;
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
@SQLDelete(sql = "UPDATE order_info set deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') where order_info_id = ?")
public class OrderInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_info_id", nullable = false)
    private Long id;

}
