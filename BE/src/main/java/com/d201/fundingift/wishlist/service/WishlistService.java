package com.d201.fundingift.wishlist.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.wishlist.dto.WishlistDto;
import com.d201.fundingift.wishlist.entity.Wishlist;
import com.d201.fundingift.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final SecurityUtil securityUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void createWishlistItem(WishlistDto request) {
        // 소비자
        Long consumerId = getConsumerId();

        // 상품, 상품 옵션 유효성 검사
        validateProductAndOption(request.getProductId(), request.getProductOptionId());

        // 위시리스트 이미 존재하는 경우
        if (findByConsumerIdAndRequest(consumerId, request) != null) {
            throw new CustomException(WISHLIST_DUPLICATED);
        }

        // 위시리스트 저장
        wishlistRepository.save(Wishlist.from(request, consumerId));
    }

    @Transactional
    public void deleteWishlistItem(WishlistDto request) {
        // 소비자
        Long consumerId = getConsumerId();

        // 상품, 상품 옵션 유효성 검사
        validateProductAndOption(request.getProductId(), request.getProductOptionId());

        // 위시리스트
        Wishlist wishlist = findByConsumerIdAndRequest(consumerId, request);

        // 위시리스트 존재하지 않는 경우
        if (wishlist == null) {
            throw new CustomException(WISHLIST_NOT_FOUND);
        }

        // 위시리스트 저장
        wishlistRepository.delete(wishlist);
    }

    public SliceList<WishlistDto> getWishlists(Integer page, Integer size) {
        // 소비자
        Long consumerId = getConsumerId();

        // 페이징
        Pageable pageable = PageRequest.of(page, size);

        // 조회
        Slice<Wishlist> wishlists = wishlistRepository.findAllSliceByConsumerId(consumerId, pageable);
        List<Object> wishlistDtos = wishlists.stream().map(WishlistDto::from).collect(Collectors.toList());

        // 전체 개수
        Long cnt = redisTemplate.opsForSet().size("wishlist:consumerId:" + consumerId);
        log.info("cnt: {}", cnt);

        boolean hasNext = true;
        if (wishlistDtos.size() < size) {
            hasNext = false;
        }
        else if (page * size + wishlistDtos.size() >= cnt) {
            hasNext = false;
        }

        // 결과 반환
        return SliceList.of(wishlistDtos, page, wishlistDtos.size(), hasNext);
    }

    private void validateProductAndOption(Long productId, Long productOptionId) {
        if (!findProductById(productId).equals(findProductOptionById(productOptionId).getProduct())) {
            throw new CustomException(PRODUCT_OPTION_MISMATCH);
        }
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    private ProductOption findProductOptionById(Long productOptionId) {
        return productOptionRepository.findByIdAndStatusIsNotInactive(productOptionId)
                .orElseThrow(() -> new CustomException(PRODUCT_OPTION_NOT_FOUND));
    }

    private Long getConsumerId() {
        return securityUtil.getConsumerId();
    }

    private Wishlist findByConsumerIdAndRequest(Long consumerId, WishlistDto request) {
        return wishlistRepository.findByConsumerIdAndProductIdAndProductOptionId
                        (consumerId, request.getProductId(), request.getProductOptionId())
                        .orElse(null);
    }

}
