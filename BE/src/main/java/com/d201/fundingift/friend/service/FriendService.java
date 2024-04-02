package com.d201.fundingift.friend.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.jwt.RedisJwtRepository;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.consumer.service.ConsumerService;
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
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private HashOperations<String, Object, Object> hashOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ConsumerRepository consumerRepository;
    private final ConsumerService consumerService;
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

                for (FriendDto friendDto : friendList) {
                    consumerRepository.findBySocialIdAndDeletedAtIsNull(friendDto.getId().toString()).ifPresent(consumer -> {
                        // 이미 레디스에 친구 정보가 존재하는지 확인
                        String friendKey = consumerId + ":" + consumer.getId();
                        if (!friendRepository.existsById(friendKey)) {
                            // 레디스에 친구 정보가 없는 경우에만 추가
                            Friend friend = Friend.from(consumerId, friendDto, consumer.getId());
                            friendRepository.save(friend);
                        }

                        // 프로필 이미지가 변경된 경우 업데이트
                        if (!Objects.equals(consumer.getProfileImageUrl(), friendDto.getProfileThumbnailImage())) {
                            consumerService.updateProfileImage(consumer.getId(), friendDto.getProfileThumbnailImage());
                        }
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

        List<Friend> friends = friendRepository.findByConsumerId(consumerId);
        log.info("Found {} friends for consumerId: {}", friends.size(), consumerId);

        List<FriendDto> friendDtos = friends.stream()
                .map(friend -> {
                    Consumer consumer = consumerRepository.findByIdAndDeletedAtIsNull(friend.getToConsumerId()).orElse(null);
                    return FriendDto.from(friend, consumer);
                })
                .filter(Objects::nonNull) // 탈퇴한 회원 제외
                .collect(Collectors.toList());

        // 친한 친구 우선으로 정렬하고, 같은 경우 이름(닉네임) 기준으로 정렬
        friendDtos.sort(Comparator.comparing(FriendDto::getFavorite).reversed() // 친한 친구 우선
                .thenComparing(FriendDto::getProfileNickname)); // 이름 기준 가나다 순 정렬

        return friendDtos;
    }

    public List<GetFriendStoryResponse> getFriendsStory() {
        Long myConsumerId = securityUtil.getConsumerId();

        List<Friend> friends = friendRepository.findByConsumerId(myConsumerId);
        List<GetFriendStoryResponse> getFriendStoryResponses = new ArrayList<>();


        for(Friend f : friends) {
            log.info(String.valueOf(f.getToConsumerId()));
            //친구의 펀딩 목록 중 진행중이고 시작일이 제일 빠른 하나 반환
            List<Funding> privateFundings = getAllByConsumerIdAndFundingStatusAndIsPrivateAndDeletedAtIsNullOrderByStartDateAsc(f, true);
            List<Funding> notPrivateFundings = getAllByConsumerIdAndFundingStatusAndIsPrivateAndDeletedAtIsNullOrderByStartDateAsc(f, false);

            Optional<Consumer> consumer = findByConsumerId(f.getToConsumerId());

            //내 친구가 소비자가 아닌 경우
            if(consumer.isEmpty())
                continue;

            //친한 친구 아닌데 친한친구 공개 펀딩만 있는 경우
            if(!privateFundings.isEmpty() && notPrivateFundings.isEmpty() && !checkingIsFavoriteFriend(consumer.get().getId(), myConsumerId))
                continue;

            if(!notPrivateFundings.isEmpty()) {
                getFriendStoryResponses.add(GetFriendStoryResponse.from(notPrivateFundings.get(0), consumer.get()));
            } else if(!privateFundings.isEmpty()) {
                getFriendStoryResponses.add(GetFriendStoryResponse.from(privateFundings.get(0), consumer.get()));
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

    private List<Funding> getAllByConsumerIdAndFundingStatusAndIsPrivateAndDeletedAtIsNullOrderByStartDateAsc(Friend f, boolean isPrivate) {
        return fundingRepository.findAllByConsumerIdAndFundingStatusAndIsPrivateAndDeletedAtIsNullOrderByStartDateAsc(f.getToConsumerId(),isPrivate);
    }

    private Optional<Consumer> findByConsumerId(Long consumerId){
        return consumerRepository.findByIdAndDeletedAtIsNull(consumerId);
    }

    private boolean checkingIsFavoriteFriend(Long toConsumerId, Long consumerId) {
        Optional<Friend> friend = friendRepository.findById(toConsumerId + ":" + consumerId);

        //보려는 펀딩 목록의 대상에 본인이 친구가 아니거나 친한 친구가 아닌 경우 -> false
        return friend.isPresent() && friend.get().getIsFavorite();
    }
}
