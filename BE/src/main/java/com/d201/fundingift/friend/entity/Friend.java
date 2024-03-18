package com.d201.fundingift.friend.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash(value = "friend")
public class Friend implements Serializable {

    @Id
    private Long friendId;
    @Indexed
    private Long consumerId;
    @Indexed
    private Long toConsumerId;
    @Indexed
    private Boolean isFavorite;

    @Builder
    private Friend(Long consumerId, Long toConsumerId, Boolean isFavorite) {
        this.consumerId = consumerId;
        this.toConsumerId = toConsumerId;
        this.isFavorite = isFavorite;
    }

    public static Friend of(Long consumerId, Long toConsumerId, Boolean isFavorite) {
        return builder()
                .consumerId(consumerId)
                .toConsumerId(toConsumerId)
                .isFavorite(isFavorite)
                .build();
    }

    public void updateIsFavorite(Boolean flag) {
        this.isFavorite = flag;
    }

}
