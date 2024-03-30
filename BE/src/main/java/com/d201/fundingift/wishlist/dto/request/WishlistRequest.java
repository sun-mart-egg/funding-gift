package com.d201.fundingift.wishlist.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WishlistRequest {

    @NotNull(message = "productId: null 이 아니어야 합니다.")
    private Long productId;

    @NotNull(message = "productOptionId: null 이 아니어야 합니다.")
    private Long productOptionId;

}
