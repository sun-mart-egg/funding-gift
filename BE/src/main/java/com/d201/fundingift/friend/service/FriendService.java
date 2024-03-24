package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private final RedisTemplate<String, List<String>> redisTemplate;
    private final JwtUtil jwtUtil;
    private static final String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";

    public String getKakaoFriends(Authentication authentication) {
        String consumerId = jwtUtil.extractUserId(authentication); // JWT 토큰에서 사용자 ID 추출
        log.info("사용자 토큰은 : "+consumerId);
        // 데이터베이스에서 사용자의 카카오 액세스 토큰을 조회
        //String kakaoAccessToken = consumerRepository.findKakaoAccessTokenByUserId(userId);
        String kakaoAccessToken = "fLKlcBUw3kWBTC6N9ztXis81aaIPyvfbapYKKw0fAAABjnEhHQFV7imzm104lw";

        // 카카오 액세스 토큰으로 카카오 API에 요청
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

        return response.getBody();
    }

    public void saveFriends(String userId, List<String> friendIds) {
        redisTemplate.opsForValue().set(userId, friendIds);
        // 필요하다면, 여기서 데이터의 만료 시간을 설정할 수 있습니다.
        // 예: redisTemplate.expire(userId, 1, TimeUnit.HOURS);
    }

    public List<String> getFriends(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }
}
