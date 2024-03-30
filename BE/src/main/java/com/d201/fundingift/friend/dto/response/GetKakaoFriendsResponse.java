package com.d201.fundingift.friend.dto.response;

import com.d201.fundingift.friend.dto.FriendDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Schema(description = "카카오 친구 목록 조회 응답")
@Getter
@ToString
public class GetKakaoFriendsResponse {

    @Schema(description = "다음 친구 목록 조회를 위한 URL", example = "https://kapi.kakao.com/v1/api/talk/friends?offset=3&limit=3&order=asc")
    private String afterUrl;

    @Schema(description = "친구 목록")
    private List<FriendDto> elements;

    @Schema(description = "친구의 총 수", example = "11")
    private Integer totalCount;

    @Schema(description = "친한 친구의 수", example = "1")
    private Integer favoriteCount;

    @Builder
    private GetKakaoFriendsResponse(String afterUrl, List<FriendDto> elements, Integer totalCount, Integer favoriteCount) {
        this.afterUrl = afterUrl;
        this.elements = elements;
        this.totalCount = totalCount;
        this.favoriteCount = favoriteCount;
    }

    public static GetKakaoFriendsResponse from(List<FriendDto> elements, int totalCount, int favoriteCount) {
        return GetKakaoFriendsResponse.builder()
                .elements(elements)
                .totalCount(totalCount)
                .favoriteCount(favoriteCount)
                .build();
    }

}