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

    @Builder
    private Wishlist(Long consumerId, Long productId) {
        this.consumerId = consumerId;
        this.productId = productId;
    }

    public static Wishlist of(Long consumerId, Long productId) {
        return builder()
                .consumerId(consumerId)
                .productId(productId)
                .build();
    }

}
