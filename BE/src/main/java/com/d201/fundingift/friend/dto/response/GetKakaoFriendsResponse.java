package com.d201.fundingift.friend.dto.response;

import lombok.Data;

@Data
public class GetKakaoFriendsResponse {
    private String afterUrl;
    private List<FriendDto> elements;
    private Integer totalCount;
    private Integer favoriteCount;

}

