package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.consumer.service.ConsumerService;
import com.d201.fundingift.friend.dto.FriendDto;
import com.d201.fundingift.friend.dto.response.GetKakaoFriendsResponse;
import com.d201.fundingift.friend.entity.Friend;
import com.d201.fundingift.friend.repository.FriendRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private HashOperations<String, Object, Object> hashOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ConsumerRepository consumerRepository;
    private final ConsumerService consumerService;
    private final JwtUtil jwtUtil;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    private static final String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public GetKakaoFriendsResponse getKakaoFriends(Authentication authentication) {
        String consumerId = jwtUtil.extractUserId(authentication); // JWT 토큰에서 사용자 ID 추출
        log.info("사용자 ID는 : " + consumerId);

        // 받아와서 입력 레디스
        // 카카오 액세스 토큰 (데이터베이스에서 조회하는 로직으로 변경 필요)
        String kakaoAccessToken = jwtUtil.getKakaoAccessToken(consumerId);
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

        for (FriendDto f : friendList) {
            consumerRepository.findBySocialId(f.getId().toString()).ifPresent(consumer -> {
                f.setConsumerId(consumer.getId());
                Friend friend = Friend.builder()
                        .consumerId(Long.parseLong(consumerId))  // 이용자 ID
                        .toConsumerId(consumer.getId())  // 친구의 사용자 ID
                        .isFavorite(f.getFavorite())
                        .build();

                saveFriend(friend);
            });
        }

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


    public void saveFriend(Friend friend) {
        // consumerId와 toConsumerId를 조합하여 고유한 키 생성
        String key = "friend:" + friend.getConsumerId() + ":" + friend.getToConsumerId();
        Map<String, Object> friendMap = new HashMap<>();
        friendMap.put("consumerId", String.valueOf(friend.getConsumerId()));
        friendMap.put("toConsumerId", String.valueOf(friend.getToConsumerId()));
        friendMap.put("isFavorite", String.valueOf(friend.getIsFavorite()));

        hashOperations.putAll(key, friendMap);
    }
    public List<FriendDto> getFriends(Long consumerId) {
        Set<String> keys = redisTemplate.keys("friend:" + consumerId + ":*");
        List<FriendDto> friendDtos = new ArrayList<>();

        for (String key : keys) {
            Map<Object, Object> friendData = hashOperations.entries(key);

            // 레디스에서 가져온 데이터로부터 필요한 정보 추출 및 설정
            Long toConsumerId = Long.valueOf((String) friendData.get("toConsumerId"));
            Boolean isFavorite = Boolean.valueOf((String) friendData.get("isFavorite"));

            Consumer consumer = consumerService.getConsumerById(toConsumerId);
            String profileNickname = consumer.getName(); // 예시: 사용자 이름으로 닉네임 설정
            String profileThumbnailImage = consumer.getProfileImageUrl(); // 예시: 프로필 이미지 URL 설정

            FriendDto friendDto = FriendDto.builder()
                    .id(null) // 카카오 소셜 ID는 여기서는 설정하지 않음
                    .consumerId(toConsumerId)
                    .favorite(isFavorite)
                    .profileNickname(profileNickname)
                    .profileThumbnailImage(profileThumbnailImage)
                    .build();

            friendDtos.add(friendDto);
        }

        return friendDtos;
    }

}
