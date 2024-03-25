package com.d201.fundingift.friend.dto.response;

import com.d201.fundingift.friend.dto.FriendDto;
import lombok.Data;

import java.util.List;

@Data
public class
GetKakaoFriendsResponse {
    private String afterUrl;
    private List<FriendDto> elements;
    private Integer totalCount;
    private Integer favoriteCount;
}

