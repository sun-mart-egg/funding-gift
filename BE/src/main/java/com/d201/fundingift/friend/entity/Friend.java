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
    private String id; //friend:consumerId:toConsumerId
    @Indexed
    private Long consumerId;
    @Indexed
    private Long toConsumerId;
    @Indexed
    private Boolean isFavorite;

    @Builder
    private Friend(String id, Long consumerId, Long toConsumerId, Boolean isFavorite) {
        this.id = id;
        this.consumerId = consumerId;
        this.toConsumerId = toConsumerId;
        this.isFavorite = isFavorite;
    }

    public void updateIsFavorite(Boolean flag) {
        this.isFavorite = flag;
    }

}
