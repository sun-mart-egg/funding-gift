package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.jwt.RedisJwtRepository;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
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
    private final FriendRepository friendRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final SecurityUtil securityUtil;
    private final JwtUtil jwtUtil;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    private static final String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public GetKakaoFriendsResponse getKakaoFriendByController() {
        Long consumerId = Long.valueOf(securityUtil.getConsumerId());
        return getKakaoFriendsByConsumerId(consumerId);
    }

    public GetKakaoFriendsResponse getKakaoFriendsByConsumerId(Long consumerId) {
        log.info("사용자 ID는 : " + consumerId);

        // 카카오 액세스 토큰 가져오기
        String kakaoAccessToken = redisJwtRepository.getKakaoAccessToken(consumerId);
        List<FriendDto> allFriends = new ArrayList<>();
        String nextUrl = FRIENDS_LIST_SERVICE_URL;

        // totalCount와 favoriteCount 초기화
        int totalCount = 0;
        int favoriteCount = 0;

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + kakaoAccessToken);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // 반복하여 모든 친구 정보 가져오기
            while (nextUrl != null) {
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<String> response = restTemplate.exchange(
                        nextUrl,
                        HttpMethod.GET,
                        entity,
                        String.class
                );

                // JSON 응답 파싱
                JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();

                // 친구 정보 파싱 및 추가
                Gson gson = new Gson();
                Type listType = new TypeToken<List<FriendDto>>(){}.getType();
                List<FriendDto> friendList = gson.fromJson(jsonResponse.get("elements"), listType);
                allFriends.addAll(friendList);

                // totalCount와 favoriteCount 업데이트
                totalCount = jsonResponse.get("total_count").getAsInt();
                favoriteCount = jsonResponse.get("favorite_count").getAsInt();

                // Redis에 친구 추가 (로직 동일하게 유지)
                for (FriendDto friendDto : friendList) {
                    consumerRepository.findBySocialIdAndDeletedAtIsNull(friendDto.getId().toString()).ifPresent(consumer -> {
                        friendDto.setConsumerId(consumer.getId());
                        Friend friend = Friend.fromFriendDto(consumerId, friendDto, consumer.getId());
                        log.info("Creating Friend with ID: " + friend.getId());
                        log.info("다음과 친구가 되었습니다: " + consumer.getId());
                        friendRepository.save(friend);
                    });
                }

                // 다음 페이지 URL 업데이트
                nextUrl = jsonResponse.has("after_url") && !jsonResponse.get("after_url").isJsonNull()
                        ? jsonResponse.get("after_url").getAsString() : null;
            }

            // GetKakaoFriendsResponse 객체 생성
            GetKakaoFriendsResponse kakaoFriendsResponse = GetKakaoFriendsResponse.from(allFriends, totalCount, favoriteCount);

            // 로그에 친구의 닉네임 출력
            allFriends.forEach(friend -> log.info(friend.getProfileNickname()));

            return kakaoFriendsResponse;
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e, consumerId);
            return null; // 적절한 예외 처리 또는 로그 출력 후 null 반환
        }
    }

    private void handleHttpClientErrorException(HttpClientErrorException e, Long consumerId) {
        if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
            log.warn("카카오 친구 목록 접근 권한이 없습니다. 사용자 ID: " + consumerId);
            // 추가적인 예외 처리 또는 사용자 안내 로직
        } else {
            throw e; // 그 외의 경우 예외를 다시 발생시킵니다.
        }
    }


    public List<FriendDto> getFriends() {
        Long consumerId = Long.valueOf(securityUtil.getConsumerId());
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
        // consumerId를 기준으로 생성한 모든 친구 관계 삭제
        Set<String> keysConsumer = stringRedisTemplate.keys("friend:" + consumerId + ":*");
        if (!keysConsumer.isEmpty()) {
            log.info("consumerId({})가 생성한 {}개의 친구 관계를 삭제합니다.", consumerId, keysConsumer.size());
            stringRedisTemplate.delete(keysConsumer);
        }

        // 다른 사용자가 consumerId를 친구로 추가한 모든 친구 관계 삭제
        Set<String> keysFriends = stringRedisTemplate.keys("friend:*:" + consumerId);
        if (!keysFriends.isEmpty()) {
            log.info("다른 사용자가 consumerId({})를 추가한 {}개의 친구 관계를 삭제합니다.", consumerId, keysFriends.size());
            stringRedisTemplate.delete(keysFriends);
        }

        log.info("consumerId({})와 관련된 모든 친구 정보가 성공적으로 삭제되었습니다.", consumerId);
    }

}
