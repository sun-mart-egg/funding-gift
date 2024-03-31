package com.d201.fundingift.address.dto.response;

import com.d201.fundingift.address.entity.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "주소 조회 응답 데이터")
public class GetAddressResponse {
    @Schema(description = "주소 ID", example = "1")
    private Long id;

    @Schema(description = "주소 이름", example = "집")
    private String name;

    @Schema(description = "기본 주소", example = "서울특별시 강남구 테헤란로")
    private String defaultAddr;

    @Schema(description = "상세 주소", example = "파이낸스빌딩 12층")
    private String detailAddr;

    @Schema(description = "우편 번호", example = "06176")
    private String zipCode;

    @Schema(description = "기본 주소 여부", example = "true")
    private Boolean isDefault;

    @Builder
    public GetAddressResponse(Long id, String name, String defaultAddr, String detailAddr, String zipCode, Boolean isDefault) {
        this.id = id;
        this.name = name;
        this.defaultAddr = defaultAddr;
        this.detailAddr = detailAddr;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
    }

    public static GetAddressResponse from(Address address) {
        return builder()
                .id(address.getId())
                .name(address.getName())
                .defaultAddr(address.getDefaultAddr())
                .detailAddr(address.getDetailAddr())
                .zipCode(address.getZipCode())
                .isDefault(address.getIsDefault())
                .build();
    }
}