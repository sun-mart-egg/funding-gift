package com.d201.fundingift.friend.dto;

import lombok.Data;

@Data
public class FriendDto {
    private Long id;
    private String uuid;
    private Boolean favorite;
    private String profileNickname;
    private String profileThumbnailImage;
}
