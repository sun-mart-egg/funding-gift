package com.d201.fundingift.funding.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.friend.entity.Friend;
import com.d201.fundingift.friend.repository.FriendRepository;
import com.d201.fundingift.funding.dto.request.PostFundingRequest;
import com.d201.fundingift.funding.dto.response.GetFundingResponse;
import com.d201.fundingift.funding.entity.AnniversaryCategory;
import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.repository.AnniversaryCategoryRepository;
import com.d201.fundingift.funding.repository.FundingRepository;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final ConsumerRepository consumerRepository;
    private final FriendRepository friendRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final AnniversaryCategoryRepository anniversaryCategoryRepository;
    private final SecurityUtil  securityUtil;


    @Transactional
    public void postFunding(PostFundingRequest postFundingRequest) {
        Consumer consumer = getConsumer();

        Product product = getProduct(postFundingRequest);

        ProductOption productOption = getProductOption(postFundingRequest);

        AnniversaryCategory anniversaryCategory = getAnniversaryCategory(postFundingRequest);

        isOver7Days(postFundingRequest.getStartDate(), postFundingRequest.getEndDate());

        fundingRepository.save(Funding.from(postFundingRequest, consumer, anniversaryCategory, product, productOption));
    }

    //내 펀딩 목록 보기, 제품명으로 검색 기능 추가
    public SliceList<GetFundingResponse> getMyFundings(String keyword, Pageable pageable) {
        Long consumerId = securityUtil.getConsumerId();

        //제품명으로 검색 안하는 경우
        if (keyword == null)
            return getMyFundingsSliceList(findAllByConsumerId(consumerId, pageable));

        //제품명으로 검색하는 경우
        return getMyFundingsSliceList(findAllByConsumerIdAndProductName(consumerId, keyword, pageable));
    }

    public SliceList<GetFundingResponse> getFriendFundings(Long friendConsumerId, String keyword, Pageable pageable) {
        Long consumerId = securityUtil.getConsumerId();

        //친구 아이디 존재 여부 확인
        findByConsumerId(friendConsumerId);

        //보려는 펀딩 목록의 대상이 자신의 친구인지 확인
        checkingFriend(consumerId, friendConsumerId);

        //보려는 펀딩 목록의 대상에 자신이 친한 친구인지 확인
        if(checkingIsFavoriteFriend(friendConsumerId, consumerId)) {
            //제품명으로 검색 안하는 경우
            if(keyword == null)
                return getMyFundingsSliceList(findAllByConsumerId(friendConsumerId, pageable));

            return getMyFundingsSliceList(findAllByConsumerIdAndProductName(friendConsumerId, keyword, pageable));
        } else {
            //제품명으로 검색 안하는 경우
            if(keyword == null)
                return getMyFundingsSliceList(findAllByConsumerIdAndIsPrivate(friendConsumerId, pageable));

            return getMyFundingsSliceList(findAllByConsumerIdAndIsPrivateAndProductName(friendConsumerId, keyword, pageable));
        }
    }

    //slice<Funding> -> SliceList<GetFundingResponse> 변경 매서드
    private SliceList<GetFundingResponse> getMyFundingsSliceList(Slice<Funding> fundings) {
        return SliceList.from(fundings.stream().map(GetFundingResponse::from).collect(Collectors.toList()), fundings.getPageable(), fundings.hasNext());
    }

    //consumerId로 펀딩 목록 찾기
    private Slice<Funding> findAllByConsumerId(Long consumerId, Pageable pageable) {
        return fundingRepository.findAllByConsumerIdAndDeletedAtIsNull(consumerId, pageable);
    }

    //consumerId, isPrivate == false로 펀딩 목록 찾기
    private Slice<Funding> findAllByConsumerIdAndIsPrivate(Long consumerId, Pageable pageable) {
        return fundingRepository.findAllByConsumerIdAndIsPrivateAndDeletedAtIsNull(consumerId, pageable);
    }

    //consumerId, 검색어로 펀딩 목록 찾기
    private Slice<Funding> findAllByConsumerIdAndProductName(Long consumerId, String keyword, Pageable pageable) {
        return fundingRepository.findAllByConsumerIdAndProductNameAndDeletedAtIsNull(consumerId, keyword, pageable);
    }

    //consumerId, isPrivate == false, 검색어로 펀딩 목록 찾기
    private Slice<Funding> findAllByConsumerIdAndIsPrivateAndProductName(Long consumerId, String keyword, Pageable pageable) {
        return fundingRepository.findAllByConsumerIdAndIsPrivateAndProductNameAndDeletedAtIsNull(consumerId, keyword, pageable);
    }

    private void findByConsumerId(Long consumerId){
        consumerRepository.findByIdAndDeletedAtIsNull(consumerId)
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));
    }

    private void checkingFriend(Long consumerId, Long toConsumerId) {
        friendRepository.findById(consumerId + ":" + toConsumerId)
                .orElseThrow(() -> new CustomException(ErrorType.FRIEND_NOT_FOUND));
    }

    private boolean checkingIsFavoriteFriend(Long toConsumerId, Long consumerId) {
        Optional<Friend> friend = friendRepository.findById(toConsumerId + ":" + consumerId);

        //보려는 펀딩 목록의 대상에 본인이 친구가 아니거나 친한 친구가 아닌 경우 -> false
        return friend.isPresent() && friend.get().getIsFavorite();
    }

    private AnniversaryCategory getAnniversaryCategory(PostFundingRequest postFundingRequest) {
        return anniversaryCategoryRepository.findById(postFundingRequest.getAnniversaryCategoryId())
                .orElseThrow(() -> new CustomException(ErrorType.ANNIVERSARY_CATEGORY_NOT_FOUND));
    }

    private ProductOption getProductOption(PostFundingRequest postFundingRequest) {
        return productOptionRepository.findByIdAndStatusIsActive(postFundingRequest.getProductOptionId())
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_OPTION_NOT_FOUND));
    }

    private Product getProduct(PostFundingRequest postFundingRequest) {
        return productRepository.findById(postFundingRequest.getProductId())
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_NOT_FOUND));
    }

    private Consumer getConsumer() {
        return securityUtil.getConsumer();
    }

    public void isOver7Days(LocalDate start, LocalDate end) {
        if (Math.abs(start.until(end).getDays()) > 7)
            throw new CustomException(ErrorType.FUNDING_DURATION_NOT_VALID);
    }
}
