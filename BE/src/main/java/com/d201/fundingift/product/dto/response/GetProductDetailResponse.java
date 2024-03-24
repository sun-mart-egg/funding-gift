package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class GetProductDetailResponse {

    private Long productId;
    private String productName;
    private Integer price;
    private String imageUrl;
    private String description;
    private Double reviewAvg; // 리뷰 로직 구현 후 삭제 예정
    private Integer reviewCnt; // 리뷰 로직 구현 후 삭제 예정
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
