package com.d201.fundingift.consumer.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.jwt.RedisJwtRepository;
import com.d201.fundingift._common.oauth2.service.OAuth2UserPrincipal;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.dto.request.PutConsumerInfoRequestDto;
import com.d201.fundingift.consumer.dto.response.GetConsumerInfoByIdResponse;
import com.d201.fundingift.consumer.dto.response.GetConsumerMyInfoResponse;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.d201.fundingift._common.response.ErrorType.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final RedisJwtRepository redisJwtRepository;
    private final SecurityUtil securityUtil;
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
        return consumerRepository.findBySocialIdAndDeletedAtIsNull(socialId);
    }

    private Consumer findById(Long id) {
        return consumerRepository.findByIdAndDeletedAtIsNull(id)
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
        return GetConsumerInfoByIdResponse.from(consumerRepository.findByIdAndDeletedAtIsNull(consumerId)
                .orElseThrow((() -> new CustomException(USER_NOT_FOUND))));
    }

    public void logoutUser() {
        Long consumerId = Long.valueOf(securityUtil.getConsumer().getId());
        String kakaoAccessToken = redisJwtRepository.getKakaoAccessToken(consumerId);
        log.info("logoutUser: "+consumerId);
        log.info("kakaoAccessToken: "+kakaoAccessToken);

        // 1. 카카오 로그아웃 API 호출  전체 삭제인지?
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/logout", entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            // 에러 처리
            throw new RuntimeException("Failed to logout from Kakao");
        }

        // 2. 로컬 로그아웃 처리: 토큰 무효화
        // 레디스에서 해당 사용자의 액세스 토큰 및 리프레시 토큰 및 카카오 액세스 토큰 삭제
        redisJwtRepository.deleteAccessToken(consumerId);
        redisJwtRepository.deleteRefreshToken(consumerId);
        redisJwtRepository.deleteKakaoAccessToken(consumerId);

    }
    @Transactional
    public void updateConsumerInfo(PutConsumerInfoRequestDto putConsumerInfoRequestDto) {
        Long consumerId = Long.valueOf(securityUtil.getConsumer().getId());
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        log.info("{} 사용자의 추가 정보 기입",consumerId);
        consumer.updateInfo(putConsumerInfoRequestDto);
    }

    @Transactional
    public void updateProfileImage(Long consumerId, String newProfileImageUrl) {
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new CustomException(CONSUMER_NOT_FOUND));

        consumer.updateProfileImageUrl(newProfileImageUrl);
    }

    @Transactional
    public void deleteConsumer(Long consumerId) {
        log.info("Attempting to delete consumer with ID: {}", consumerId);
        consumerRepository.findByIdAndDeletedAtIsNull(consumerId).ifPresentOrElse(
                consumer -> {
                    consumerRepository.delete(consumer);
                    log.info("Deleted consumer with ID: {}", consumerId);
                },
                () -> log.warn("Consumer with ID: {} not found, cannot delete", consumerId)
        );
    }

}
