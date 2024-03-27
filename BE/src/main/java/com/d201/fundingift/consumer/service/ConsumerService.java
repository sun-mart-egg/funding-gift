package com.d201.fundingift.consumer.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.oauth2.service.OAuth2UserPrincipal;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.dto.response.GetConsumerInfoByIdResponse;
import com.d201.fundingift.consumer.dto.response.GetConsumerMyInfoResponse;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.d201.fundingift._common.response.ErrorType.USER_NOT_FOUND;
import static com.d201.fundingift._common.response.ErrorType.USER_UNAUTHORIZED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final SecurityUtil securityUtil;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    // 회원가입
    @Transactional
    public Long saveOAuth2User(OAuth2UserPrincipal principal) {
        Consumer consumer = Consumer.builder()
                .socialId(principal.getUserInfo().getId())
                .email(principal.getUserInfo().getEmail())
                .name(principal.getUserInfo().getName())
                .profileImageUrl(principal.getUserInfo().getProfileImageUrl())
                // 필요한 다른 필드 설정
                .build();

        return consumerRepository.save(consumer).getId();
    }

    // socialId로 회원 찾기.
    public Optional<Consumer> findBySocialId(String socialId) {
        return consumerRepository.findBySocialId(socialId);
    }

    private Consumer findById(Long id) {
        return consumerRepository.findById(id)
                .orElseThrow((() -> new CustomException(USER_NOT_FOUND)));
    }

    // 내 정보 조회
    public GetConsumerMyInfoResponse getConsumerMyInfo(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new CustomException(USER_UNAUTHORIZED);
        }

        String id;
        if (authentication.getPrincipal() instanceof OAuth2UserPrincipal) {
            id = ((OAuth2UserPrincipal) authentication.getPrincipal()).getUserInfo().getId();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            id = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            throw new CustomException(USER_NOT_FOUND);
        }

        return GetConsumerMyInfoResponse.from(findById(Long.parseLong(id)));
    }

    // 소비자 프로필 조회
    public GetConsumerInfoByIdResponse getConsumerInfoById(Long consumerId) {
        return GetConsumerInfoByIdResponse.from(consumerRepository.findById(consumerId)
                .orElseThrow((() -> new CustomException(USER_NOT_FOUND))));
    }

    public void logoutUser() {
        String consumerId = securityUtil.getConsumer().getName();
        String kakaoAccessToken = jwtUtil.getKakaoAccessToken(consumerId);

        // 1. 로컬 로그아웃 처리: 토큰 무효화
        // 레디스에서 해당 사용자의 액세스 토큰 및 리프레시 토큰 및 카카오 액세스 토큰 삭제
        jwtUtil.deleteAccessToken(consumerId);
        jwtUtil.deleteRefreshToken(consumerId);
        jwtUtil.deleteKakaoAccessToken(consumerId);

        // 2. 카카오 로그아웃 API 호출  전체 삭제인지?
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/logout", entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            // 에러 처리
            throw new RuntimeException("Failed to logout from Kakao");
        }
    }

    public Consumer getConsumerById(Long consumerId) {
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RuntimeException("Consumer not found with id: " + consumerId));
    }

    public void deleteConsumer(Long consumerId) {
        consumerRepository.deleteById(consumerId);
    }
}
