package com.d201.fundingift.friend.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.friend.dto.FriendDto;
import com.d201.fundingift.friend.dto.response.GetKakaoFriendsResponse;
import com.d201.fundingift.friend.entity.Friend;
import com.d201.fundingift.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "friends", description = "친구 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class FriendController {
    private final FriendService friendService;

    @Operation(summary = "내 카카오 친구목록 불러오기",
            description = "내 카카오 계정의 친구정보를 조회합니다. `Token`"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true)
    })
    @GetMapping("/kakao")
    public SuccessResponse<GetKakaoFriendsResponse> getKakaoFriends(Authentication authentication) {
        GetKakaoFriendsResponse friendsResponse = friendService.getKakaoFriends(authentication);

        // 로그에 친구의 닉네임 출력
        for (FriendDto friend : friendsResponse.getElements()) {
            log.info(friend.getProfileNickname());
        }

        // 친구 목록을 Redis에 저장하려면 여기에서 friendService.saveFriends를 호출합니다.
        // 예: friendService.saveFriends(userId, friendList);
        return ResponseUtils.ok(friendsResponse, SuccessType.GET_KAKAO_FRIEND_INFO_SUCCESS);
    }

    @Operation(summary = "친구 목록 조회",
            description = "ID에 해당하는 소비자의 친구정보를 조회합니다. `Token`"
    )
    @GetMapping("/{consumer-id}")
    public ResponseEntity<List<FriendDto>> getFriends(@PathVariable("consumer-id") Long consumerId) {
        List<FriendDto> friendDtos = friendService.getFriends(consumerId);
        return ResponseEntity.ok(friendDtos);
    }
}
