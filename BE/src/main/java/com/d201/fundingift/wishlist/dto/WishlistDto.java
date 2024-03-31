package com.d201.fundingift.wishlist.dto;

import com.d201.fundingift.wishlist.entity.Wishlist;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "WishlistDto", description = "위시리스트 요청/응답")
public class WishlistDto {

    @NotNull(message = "productId: null 이 아니어야 합니다.")
    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @NotNull(message = "productOptionId: null 이 아니어야 합니다.")
    @Schema(description = "상품 옵션 ID", example = "1")
    private Long productOptionId;

    @Builder
    private WishlistDto(Long productId, Long productOptionId) {
        this.productId = productId;
        this.productOptionId = productOptionId;
    }

    public static WishlistDto from(Wishlist wishlist) {
        return builder()
                .productId(wishlist.getProductId())
                .productOptionId(wishlist.getProductOptionId())
                .build();
    }

}
