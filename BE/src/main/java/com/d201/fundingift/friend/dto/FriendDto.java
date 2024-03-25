package com.d201.fundingift.friend.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
public class FriendDto {
    private Long id;
    private String uuid;
    private Boolean favorite;
    @SerializedName("profile_nickname")
    private String profileNickname;
    @SerializedName("profile_thumbnail_image")
    private String profileThumbnailImage;

    @Builder
    public FriendDto(Long id, String uuid, Boolean favorite, String profileNickname, String profileThumbnailImage) {
        this.id = id;
        this.uuid = uuid;
        this.favorite = favorite;
        this.profileNickname = profileNickname;
        this.profileThumbnailImage = profileThumbnailImage;
    }
}


