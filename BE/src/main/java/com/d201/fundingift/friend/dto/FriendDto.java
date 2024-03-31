package com.d201.fundingift.friend.dto;

import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.friend.entity.Friend;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
public class FriendDto {
    private Long id; // 카카오 소셜 ID
    private Long consumerId; // 소비자 ID
    private Boolean favorite;
    @SerializedName("profile_nickname")
    private String profileNickname;
    @SerializedName("profile_thumbnail_image")
    private String profileThumbnailImage;

    @Builder
    public FriendDto(Long id,Long consumerId, Boolean favorite, String profileNickname, String profileThumbnailImage) {
        this.id = id;
        this.consumerId = consumerId;
        this.favorite = favorite;
        this.profileNickname = profileNickname;
        this.profileThumbnailImage = profileThumbnailImage;
    }

    public static FriendDto from(Friend friend, String profileNickname, String profileThumbnailImage) {
        return FriendDto.builder()
                .consumerId(friend.getToConsumerId())
                .favorite(friend.getIsFavorite())
                .profileNickname(profileNickname)
                .profileThumbnailImage(profileThumbnailImage)
                .build();
    }

    public static FriendDto from(Friend friend, Consumer consumer) {
        return FriendDto.builder()
                .consumerId(friend.getToConsumerId())
                .favorite(friend.getIsFavorite())
                .profileNickname(consumer != null ? consumer.getName() : "탈퇴한 회원")
                .profileThumbnailImage(consumer != null ? consumer.getProfileImageUrl() : null)
                .build();
    }

}


