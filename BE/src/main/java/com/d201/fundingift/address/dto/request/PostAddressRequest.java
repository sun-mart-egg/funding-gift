package com.d201.fundingift.address.dto.request;

import com.d201.fundingift.address.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PostAddressRequest {
    @Size(max = 10, message = "이름은 최대 10자까지 가능합니다")
    private String name;

    @NotBlank(message = "기본 주소는 비어 있을 수 없습니다")
    @Size(max = 50, message = "기본 주소는 최대 50자까지 가능합니다")
    private String defaultAddr;

    @Size(max = 50, message = "상세 주소는 최대 50자까지 가능합니다")
    private String detailAddr;

    @NotBlank(message = "우편번호는 비어 있을 수 없습니다")
    @Size(max = 10, message = "우편번호는 최대 10자까지 가능합니다")
    private String zipCode;

    private Boolean isDefault;

    public static PostAddressRequest from(Address address) {
        return PostAddressRequest.builder()
                .name(address.getName())
                .defaultAddr(address.getDefaultAddr())
                .detailAddr(address.getDetailAddr())
                .zipCode(address.getZipCode())
                .isDefault(address.getIsDefault())
                .build();
    }
}