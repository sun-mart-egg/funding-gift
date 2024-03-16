package com.d201.fundingift.remittance.entity;

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
@SQLDelete(sql = "UPDATE remittance set deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') where remittance_id = ?")
public class RemittanceInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "remittance_info_id", nullable = false)
    private Long id;

}
