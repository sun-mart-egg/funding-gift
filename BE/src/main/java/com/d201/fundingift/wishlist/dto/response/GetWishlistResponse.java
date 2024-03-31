package com.d201.fundingift.wishlist.dto.response;

import com.d201.fundingift.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "GetWishlistResponse", description = "위시리스트 목록 조회 응답")
public class GetWishlistResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "금 원터치 링귀걸이 원형 컷팅 데일리 여성")
    private String productName;

    @Schema(description = "상품 이미지 URL", example = "상품 이미지 URL")
    private String imageUrl;

    @Schema(description = "상품 가격", example = "118000")
    private Integer price;

    @Builder
    private GetWishlistResponse(Long productId, String productName, String imageUrl, Integer price) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static GetWishlistResponse from(Product product) {
        return builder()
                .productId(product.getId())
                .productName(product.getName())
                .imageUrl(product.getImage())
                .price(product.getPrice())
                .build();
    }

}
