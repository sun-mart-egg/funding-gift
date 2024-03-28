package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.jwt.RedisJwtRepository;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.friend.dto.FriendDto;
import com.d201.fundingift.friend.dto.response.GetKakaoFriendsResponse;
import com.d201.fundingift.friend.entity.Friend;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private HashOperations<String, Object, Object> hashOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ConsumerRepository consumerRepository;
    private final RedisJwtRepository redisJwtRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtUtil jwtUtil;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    private static final String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public GetKakaoFriendsResponse getKakaoFriendByAuthentication(Authentication authentication) {
        Long consumerId = Long.valueOf(jwtUtil.extractUserId(authentication));
        return getKakaoFriendsByConsumerId(consumerId);
    }

    public GetKakaoFriendsResponse getKakaoFriendsByConsumerId(Long consumerId) {
        log.info("사용자 ID는 : " + consumerId);

        // 카카오 액세스 토큰 가져오기
        String kakaoAccessToken = redisJwtRepository.getKakaoAccessToken(consumerId);

        try {
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

            // Redis에 친구 추가.
            for (FriendDto f : friendList) {
                consumerRepository.findBySocialIdAndDeletedAtIsNull(f.getId().toString()).ifPresent(consumer -> {
                    f.setConsumerId(consumer.getId());
                    Friend friend = Friend.builder()
                            .consumerId(consumerId)  // 이용자 ID
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
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                log.warn("카카오 친구 목록 접근 권한이 없습니다. 사용자 ID: " + consumerId);
                // 필요한 경우 사용자에게 권한 부여 요청 등의 추가 조치를 안내할 수 있습니다.
                // 여기에서는 빈 목록을 반환하거나, 권한 없음을 나타내는 응답을 반환할 수 있습니다.
                return null; // 빈 응답 객체 반환
            }
            throw e; // 그 외의 경우 예외를 다시 발생시킵니다.
        }
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

            Optional<Consumer> consumerOptional = consumerRepository.findByIdAndDeletedAtIsNull(toConsumerId);
            // 기본값 설정
            String profileNickname = "Unknown";
            String profileThumbnailImage = "";
            if (consumerOptional.isPresent()) {
                Consumer consumer = consumerOptional.get();
                // consumer 객체를 사용하는 로직
                profileNickname = consumer.getName(); // 사용자 이름으로 닉네임 설정
                profileThumbnailImage = consumer.getProfileImageUrl(); // 프로필 이미지 URL 설정
            } else {
                // consumer 객체를 찾을 수 없는 경우의 처리 로직
            }

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

    @Transactional
    public void deleteAllFriendsByConsumerId(Long consumerId) {
        // consumerId를 기반으로 모든 친구 관련 키 찾기
        Set<String> keys = stringRedisTemplate.keys("friend:" + consumerId + ":*");
        if (keys.isEmpty()) {
            // 해당 consumerId로 저장된 친구가 없는 경우
            log.info("해당 consumerId({})로 저장된 친구가 없습니다.", consumerId);
            return;
        }

        // 찾은 키를 이용해 해당 사용자의 모든 친구 정보 삭제
        log.info("consumerId({})에 대한 {}개의 친구 정보를 삭제합니다.", consumerId, keys.size());
        stringRedisTemplate.delete(keys);
        log.info("consumerId({})에 대한 모든 친구 정보가 성공적으로 삭제되었습니다.", consumerId);
    }
}
