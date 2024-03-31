package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.jwt.RedisJwtRepository;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.friend.dto.FriendDto;
import com.d201.fundingift.friend.dto.response.GetFriendStoryResponse;
import com.d201.fundingift.friend.dto.response.GetKakaoFriendsResponse;
import com.d201.fundingift.friend.entity.Friend;

import com.d201.fundingift.friend.repository.FriendRepository;
import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.repository.FundingRepository;
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

import static com.d201.fundingift._common.response.ErrorType.*;

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
    private final FundingRepository fundingRepository;
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
                        Friend friend = Friend.from(consumerId, friendDto, consumer.getId());
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
            new CustomException(KAKAO_FRIEND_NOT_FOUND);
            return null; // 적절한 예외 처리 또는 로그 출력 후 null 반환
        }
    }

    public List<FriendDto> getFriends() {
        Long consumerId = Long.valueOf(securityUtil.getConsumerId());
        log.info("Retrieving friends for consumerId: {}", consumerId);

        List<Friend> friends = friendRepository.findByConsumerId(consumerId); // FriendRepository에서 consumerId로 조회
        log.info("Found {} friends for consumerId: {}", friends.size(), consumerId);

        List<FriendDto> friendDtos = new ArrayList<>();

        for (Friend friend : friends) {
            Optional<Consumer> consumerOptional = consumerRepository.findByIdAndDeletedAtIsNull(friend.getToConsumerId());

            String profileNickname = "탈퇴한 회원";
            String profileThumbnailImage = "";

            if (consumerOptional.isPresent()) {
                Consumer consumer = consumerOptional.get();
                profileNickname = consumer.getName();
                profileThumbnailImage = consumer.getProfileImageUrl();
            }

            // FriendDto 생성
            FriendDto friendDto = FriendDto.from(friend, profileNickname, profileThumbnailImage);
            friendDtos.add(friendDto);
        }

        return friendDtos;
    }

    public List<GetFriendStoryResponse> getFriendsStory() {
        Long myConsumerId = securityUtil.getConsumerId();

        List<Friend> friends = friendRepository.findByConsumerId(myConsumerId);
        List<GetFriendStoryResponse> getFriendStoryResponses = new ArrayList<>();


        for(Friend f : friends) {
            log.info(String.valueOf(f.getToConsumerId()));
            //친구의 펀딩 목록 중 진행중이고 시작일이 제일 빠른 하나 반환
            Optional<Funding> funding = getOneByConsumerIdAndFundingStatusAndDeletedAtIsNullOrderByStartDateAsc(f);

            Optional<Consumer> consumer = findByConsumerId(f.getToConsumerId());

            if(funding.isPresent() && consumer.isPresent()) {
                getFriendStoryResponses.add(GetFriendStoryResponse.from(funding.get(), consumer.get()));
            }
        }

        Collections.sort(getFriendStoryResponses);

        return getFriendStoryResponses;
    }

    @Transactional
    public void toggleFavorite(Long toConsumerId) {
        Long consumerId = Long.valueOf(securityUtil.getConsumerId());
        log.info("consumerId {}",consumerId);
        log.info("toConsumerId {}",toConsumerId);
        Friend friend = friendRepository.findByConsumerIdAndToConsumerId(consumerId, toConsumerId).orElseThrow(() -> new CustomException(FRIEND_NOT_FOUND));
        if (friend != null) {
            friend.toggleFavorite();
            friendRepository.save(friend);
        } else {
            throw new CustomException(FRIEND_RELATIONSHIP_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteAllFriendsByConsumerId(Long consumerId) {
        // consumerId를 기준으로 생성한 모든 친구 관계 삭제
        friendRepository.deleteByConsumerId(consumerId);
        log.info("consumerId({})가 생성한 모든 친구 관계를 삭제했습니다.", consumerId);

        // 다른 사용자가 consumerId를 친구로 추가한 모든 친구 관계 삭제
        friendRepository.deleteByToConsumerId(consumerId);
        log.info("다른 사용자가 consumerId({})를 추가한 모든 친구 관계를 삭제했습니다.", consumerId);

        log.info("consumerId({})와 관련된 모든 친구 정보가 성공적으로 삭제되었습니다.", consumerId);
    }

    private Optional<Funding> getOneByConsumerIdAndFundingStatusAndDeletedAtIsNullOrderByStartDateAsc(Friend f) {
        return fundingRepository.findOneByConsumerIdAndFundingStatusAndDeletedAtIsNullOrderByStartDateAsc(f.getToConsumerId());
    }

    private Optional<Consumer> findByConsumerId(Long consumerId){
        return consumerRepository.findByIdAndDeletedAtIsNull(consumerId);
    }
}
