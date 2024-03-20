package com.d201.fundingift.consumer.service;

import com.d201.fundingift._common.oauth2.service.OAuth2UserPrincipal;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ConsumerService {
    private final ConsumerRepository consumerRepository;

    @Autowired
    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

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


    // 소비자 프로필 조회
    public Object getConsumerProfile(Long consumerId) {
        // consumerId를 사용하여 소비자 프로필 조회 로직 구현
        return null;
    }

    // 내 정보 조회
    public Object getMyInfo(Authentication authentication) {
        // Authentication 객체를 사용하여 현재 로그인된 사용자의 정보 조회 로직 구현
        return null;
    }

    // 친구 목록 생성 (회원가입 시)
    // 실제 서비스에서는 회원가입 로직과 연동되어야 합니다.

    // 친구 목록 조회
    public Object getFriendsList(Integer page, Integer size, String sort, Boolean isFavorite) {
        // 페이지네이션, 정렬, 즐겨찾기 필터 등을 적용하여 친구 목록 조회 로직 구현
        return null;
    }
}
