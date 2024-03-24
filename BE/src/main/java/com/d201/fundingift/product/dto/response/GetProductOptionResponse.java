package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.entity.status.ProductOptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "GetProductOptionResponse", description = "상품 옵션 조회 응답")
public class GetProductOptionResponse {

    @Schema(description = "상품 옵션 ID", example = "1")
    private Long id;

    @Schema(description = "상품 옵션 이름", example = "2개")
    private String name;

    @Schema(description = "상품 옵션 가격", example = "10000")
    private Integer price;

    @Schema(description = "상품 옵션 상태", example = "SOLD_OUT")
    private ProductOptionStatus status;

    @Builder
    private GetProductOptionResponse(Long id, String name, Integer price, ProductOptionStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public static GetProductOptionResponse from(ProductOption productOption) {
        return builder()
                .id(productOption.getId())
                .name(productOption.getName())
                .price(productOption.getPrice())
                .status(productOption.getStatus())
                .build();
    }

}
