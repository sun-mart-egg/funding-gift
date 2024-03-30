package com.d201.fundingift.friend.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.friend.dto.FriendDto;
import com.d201.fundingift.friend.dto.response.GetKakaoFriendsResponse;
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
    public SuccessResponse<GetKakaoFriendsResponse> getKakaoFriendByAuthentication() {
        GetKakaoFriendsResponse friendsResponse = friendService.getKakaoFriendByController();
        return ResponseUtils.ok(friendsResponse, SuccessType.GET_KAKAO_FRIEND_INFO_SUCCESS);
    }

    @Operation(summary = "내 친구 목록 조회 (소비자)",
            description = "내 친구들의 친구정보를 조회합니다. `Token`"
    )
    @GetMapping
    public ResponseEntity<List<FriendDto>> getFriends() {
        List<FriendDto> friendDtos = friendService.getFriends();
        return ResponseEntity.ok(friendDtos);
    }

    @Operation(summary = "친한친구 설정 변경",
            description = "해당 친구와의 친한친구 관계를 토글합니다. `Token`"
    )
    @PatchMapping("/{to-consumer-id}/toggle-favorite")
    public ResponseEntity<?> toggleFavorite(@PathVariable("to-consumer-id") Long toConsumerId) {
        friendService.toggleFavorite(toConsumerId);
        return ResponseEntity.ok().build();
    }
}
