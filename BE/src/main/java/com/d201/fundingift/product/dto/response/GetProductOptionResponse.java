package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.entity.status.ProductOptionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GetProductOptionResponse {

    private Long id;
    private String name;
    private Integer price;
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
