package com.d201.fundingift.friend.entity;

import com.d201.fundingift.friend.dto.FriendDto;
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

    public void toggleFavorite() {
        this.isFavorite = !this.isFavorite;
    }

    // Friend 엔티티 내에 추가
    public static Friend fromFriendDto(Long consumerId, FriendDto friendDto, Long toConsumerId) {
        return Friend.builder()
                .id(consumerId + ":" + toConsumerId)
                .consumerId(consumerId)
                .toConsumerId(toConsumerId)
                .isFavorite(friendDto.getFavorite())
                .build();
    }
}
