package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Schema(name = "GetProductDetailResponse", description = "상품 상세 조회 응답")
public class GetProductDetailResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "더마비 데일리 모이스처 바디로션 400ml")
    private String productName;

    @Schema(description = "상품 가격", example = "16580")
    private Integer price;

    @Schema(description = "이미지 URL", example = "-")
    private String imageUrl;

    @Schema(description = "설명", example = "피부타입: 모든 피부, 용기형태: 펌프형, 기능:고보습, 유통기한: 2025-06-12 이거나 그 이후인 상품")
    private String description;

    @Schema(description = "리뷰 개수", example = "10")
    private Integer reviewCnt; // 리뷰 로직 구현 후 삭제 예정

    @Schema(description = "리뷰 평점", example = "4.5")
    private Double reviewAvg; // 리뷰 로직 구현 후 삭제 예정

    private List<GetProductOptionResponse> options;

    @Builder
    private GetProductDetailResponse(Long productId, String productName, Integer price, String imageUrl, String description, Double reviewAvg, Integer reviewCnt, List<GetProductOptionResponse> options) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.reviewAvg = reviewAvg;
        this.reviewCnt = reviewCnt;
        this.options = options;
    }

    public static GetProductDetailResponse from(Product product, List<GetProductOptionResponse> options) {
        return builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImage())
                .description(product.getDescription())
                .reviewAvg(product.getReviewAvg())
                .reviewCnt(product.getReviewCnt())
                .options(options)
                .build();
    }

}
