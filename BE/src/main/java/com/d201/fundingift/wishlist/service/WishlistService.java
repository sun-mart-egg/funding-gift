package com.d201.fundingift.wishlist.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.wishlist.dto.request.WishlistRequest;
import com.d201.fundingift.wishlist.dto.response.GetWishlistResponse;
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
    private final SecurityUtil securityUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void createWishlistItem(WishlistRequest request) {
        // 소비자
        Long consumerId = getConsumerId();

        // 상품 ID
        Long productId = request.getProductId();

        // 상품 유효성 검사
        validateProductId(productId);

        // 위시리스트 이미 존재하는 경우
        if (findByConsumerIdAndProductId(consumerId, productId) != null) {
            throw new CustomException(WISHLIST_DUPLICATED);
        }

        // 위시리스트 저장
        wishlistRepository.save(Wishlist.of(consumerId, productId));
    }

    @Transactional
    public void deleteWishlistItem(WishlistRequest request) {
        // 소비자
        Long consumerId = getConsumerId();

        // 상품 유효성 검사
        validateProductId(request.getProductId());

        // 위시리스트
        Wishlist wishlist = findByConsumerIdAndProductId(consumerId, request.getProductId());

        // 위시리스트 존재하지 않는 경우
        if (wishlist == null) {
            throw new CustomException(WISHLIST_NOT_FOUND);
        }

        // 위시리스트 저장
        wishlistRepository.delete(wishlist);
    }

    public SliceList<GetWishlistResponse> getWishlists(Integer page, Integer size) {
        // 소비자
        Long consumerId = getConsumerId();

        // 페이징
        Pageable pageable = PageRequest.of(page, size);

        // 조회 -> dto
        List<Object> wishlistDtos = getWishlistDtos(consumerId, pageable);

        // 다음 페이지 존재 여부
        boolean hasNext = getHasNext(page, size, wishlistDtos.size(), consumerId);

        // 결과 반환
        return SliceList.of(wishlistDtos, page, wishlistDtos.size(), hasNext);
    }

    private boolean getHasNext(Integer page, Integer requestSize, Integer resultSize, Long consumerId) {
        Long cnt = redisTemplate.opsForSet().size("wishlist:consumerId:" + consumerId);
        log.info("getWishlistCntByConsumerId: {}", cnt);

        if (resultSize < requestSize || page * requestSize + resultSize >= cnt) {
            return false;
        }

        return true;
    }

    private void validateProductId(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    private List<Object> getWishlistDtos(Long consumerId, Pageable pageable) {
        // 위시리스트 조회
        Slice<Wishlist> wishlists = findAllWishlistByConsumerId(consumerId, pageable);

        // 상품 목록
        List<Product> products = wishlists.stream()
                .map(w -> findProductById(w.getProductId())).collect(Collectors.toList());

        // dto로 반환
        return products.stream().map(GetWishlistResponse::from).collect(Collectors.toList());
    }

    private Slice<Wishlist> findAllWishlistByConsumerId(Long consumerId, Pageable pageable) {
        return wishlistRepository.findAllSliceByConsumerId(consumerId, pageable);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    private Long getConsumerId() {
        return securityUtil.getConsumerId();
    }

    private Wishlist findByConsumerIdAndProductId(Long consumerId, Long productId) {
        return wishlistRepository.findByConsumerIdAndProductId(consumerId, productId)
                        .orElse(null);
    }

}
