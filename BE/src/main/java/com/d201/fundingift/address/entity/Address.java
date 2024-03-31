package com.d201.fundingift.address.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.address.dto.request.PostAddressRequest;
import com.d201.fundingift.consumer.entity.Consumer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Column(nullable = true, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String defaultAddr;

    @Column(nullable = true, length = 50)
    private String detailAddr;

    @Column(nullable = false, length = 10)
    private String zipCode;

    @Column(nullable = false)
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    private Consumer consumer;

    @Builder
    private Address(String name, String defaultAddr, String detailAddr, String zipCode, Boolean isDefault, Consumer consumer) {
        this.name = name;
        this.defaultAddr = defaultAddr;
        this.detailAddr = detailAddr;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
        this.consumer = consumer;
    }

    public static Address from(PostAddressRequest request, Consumer consumer) {
        return Address.builder()
                .name(request.getName())
                .defaultAddr(request.getDefaultAddr())
                .detailAddr(request.getDetailAddr())
                .zipCode(request.getZipCode())
                .isDefault(request.getIsDefault())
                .consumer(consumer)
                .build();
    }

    public void updateFrom(PostAddressRequest request) {
        this.name = request.getName();
        this.defaultAddr = request.getDefaultAddr();
        this.detailAddr = request.getDetailAddr();
        this.zipCode = request.getZipCode();
        this.isDefault = request.getIsDefault();
    }
}
