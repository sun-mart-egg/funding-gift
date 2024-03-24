package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GetProductResponse {

    private Long productId;
    private String productName;
    private Integer price;
    private Integer reviewCnt;
    private Double reviewAvg;

    @Builder
    private GetProductResponse(Long productId, String productName, Integer price, Integer reviewCnt, Double reviewAvg) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.reviewCnt = reviewCnt;
        this.reviewAvg = reviewAvg;
    }

    public static GetProductResponse from(Product product) {
        return builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .reviewCnt(product.getReviewCnt())
                .reviewAvg(product.getReviewAvg())
                .build();
    }

}
