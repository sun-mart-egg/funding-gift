package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.consumer.service.ConsumerService;
import com.d201.fundingift.friend.dto.FriendDto;
import com.d201.fundingift.friend.dto.response.GetKakaoFriendsResponse;
import com.d201.fundingift.friend.entity.Friend;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private final RedisTemplate<String, List<String>> redisTemplate;
    private final ConsumerService consumerService;
    private final JwtUtil jwtUtil;
    private static final String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public GetKakaoFriendsResponse getKakaoFriends(Authentication authentication) {
        String consumerId = jwtUtil.extractUserId(authentication); // JWT 토큰에서 사용자 ID 추출
        log.info("사용자 ID는 : " + consumerId);

        // 받아와서 입력 레디스
        // 카카오 액세스 토큰 (데이터베이스에서 조회하는 로직으로 변경 필요)
        String kakaoAccessToken = consumerService.getAccessToken(consumerId);
        // 카카오 API에 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                FRIENDS_LIST_SERVICE_URL,
                HttpMethod.GET,
                entity,
                String.class
        );

        // 최상위 JSON 객체 파싱
        JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();

        // "elements" 키에 해당하는 JSON 배열을 가져와서 List<FriendDto>로 변환
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FriendDto>>(){}.getType();
        List<FriendDto> friendList = gson.fromJson(jsonResponse.get("elements"), listType);

        String afterUrl = null;
        if (jsonResponse.has("after_url") && !jsonResponse.get("after_url").isJsonNull()) {
            afterUrl = jsonResponse.get("after_url").getAsString();
        }

        // GetKakaoFriendsResponse 객체 생성
        GetKakaoFriendsResponse kakaoFriendsResponse = GetKakaoFriendsResponse.builder()
                .afterUrl(afterUrl)
                .elements(friendList)
                .totalCount(jsonResponse.get("total_count").getAsInt())
                .favoriteCount(jsonResponse.get("favorite_count").getAsInt())
                .build();

        return kakaoFriendsResponse;
    }


    public void saveFriends(Long userId, List<Friend> friends) {
        HashOperations<String, Long, Friend> hashOps = redisTemplate.opsForHash();
        // 친구 목록을 레디스에 저장
        friends.forEach(friend -> hashOps.put("friends:" + userId, friend.getToConsumerId(), friend));
    }

    public List<Friend> getFriends(Long userId) {
        HashOperations<String, Long, Friend> hashOps = redisTemplate.opsForHash();
        // 레디스에서 사용자의 친구 목록을 조회
        return hashOps.values("friends:" + userId);
    }
}
