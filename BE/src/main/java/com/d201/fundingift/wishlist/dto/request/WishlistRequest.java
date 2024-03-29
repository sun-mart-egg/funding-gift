package com.d201.fundingift.wishlist.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WishlistRequest {

    private Long productId;
    private Long productOptionId;

}
