package com.d201.fundingift.wishlist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "WishlistDto", description = "위시리스트 요청/응답")
public class WishlistRequest {

    @NotNull(message = "productId: null 이 아니어야 합니다.")
    @Schema(description = "상품 ID", example = "1")
    private Long productId;


}
