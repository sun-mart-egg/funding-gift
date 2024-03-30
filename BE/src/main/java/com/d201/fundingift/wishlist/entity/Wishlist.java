package com.d201.fundingift.wishlist.entity;

import com.d201.fundingift.wishlist.dto.WishlistDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash(value = "wishlist")
public class Wishlist implements Serializable {

    @Id
    private String wishlistId;

    @Indexed
    private Long consumerId;

    @Indexed
    private Long productId;

    @Indexed
    private Long productOptionId;

    @Builder
    private Wishlist(Long consumerId, Long productId, Long productOptionId) {
        this.consumerId = consumerId;
        this.productId = productId;
        this.productOptionId = productOptionId;
    }

    public static Wishlist from(WishlistDto request, Long consumerId) {
        return builder()
                .consumerId(consumerId)
                .productId(request.getProductId())
                .productOptionId(request.getProductOptionId())
                .build();
    }

}
