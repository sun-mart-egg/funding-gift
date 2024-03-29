package com.d201.fundingift.wishlist.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.wishlist.dto.request.WishlistRequest;
import com.d201.fundingift.wishlist.entity.Wishlist;
import com.d201.fundingift.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.d201.fundingift._common.response.ErrorType.PRODUCT_NOT_FOUND;
import static com.d201.fundingift._common.response.ErrorType.WISHLIST_DUPLICATED;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void createWishlistItem(WishlistRequest request) {
        // 소비자
        Long consumerId = getConsumerId();

        // 상품 ID 유효성 검사
        validateProductId(request.getProductId());

        // 위시리스트 이미 존재하는 경우
        if (findByConsumerIdAndRequest(consumerId, request) != null) {
            throw new CustomException(WISHLIST_DUPLICATED);
        }

        // 위시리스트 저장
        wishlistRepository.save(Wishlist.from(request, consumerId));
    }

    private void validateProductId(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    private Long getConsumerId() {
        return securityUtil.getConsumerId();
    }

    private Wishlist findByConsumerIdAndRequest(Long consumerId, WishlistRequest request) {
        return wishlistRepository.findByConsumerIdAndProductIdAndProductOptionId
                        (consumerId, request.getProductId(), request.getProductOptionId())
                        .orElse(null);
    }

}
