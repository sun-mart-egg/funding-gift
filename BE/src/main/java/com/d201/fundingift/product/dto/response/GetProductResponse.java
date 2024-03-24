package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "GetProductResponse", description = "상품 목록 조회 응답")
public class GetProductResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "더마비 데일리 모이스처 바디로션 400ml")
    private String productName;

    @Schema(description = "상품 가격", example = "16580")
    private Integer price;

    @Schema(description = "리뷰 개수", example = "10")
    private Integer reviewCnt;

    @Schema(description = "리뷰 평점", example = "4.5")
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
