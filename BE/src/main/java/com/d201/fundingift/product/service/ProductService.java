package com.d201.fundingift.product.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.product.dto.response.GetProductCategoryResponse;
import com.d201.fundingift.product.dto.response.GetProductDetailResponse;
import com.d201.fundingift.product.dto.response.GetProductOptionResponse;
import com.d201.fundingift.product.dto.response.GetProductResponse;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.repository.ProductCategoryRepository;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.wishlist.entity.Wishlist;
import com.d201.fundingift.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final WishlistRepository wishlistRepository;
    private final SecurityUtil securityUtil;

    // 카테고리 리스트 조회
    public List<GetProductCategoryResponse> getCategories() {
        return productCategoryRepository.findAllByDeletedAtIsNull()
                .stream().map(GetProductCategoryResponse::from)
                .collect(Collectors.toList());
    }

    // 상품 리스트 조회
    public SliceList<GetProductResponse> getProducts(Integer categoryId, String keyword, Integer page, Integer size, Integer sort) {
        // 페이징 객체
        Pageable pageable = PageRequest.of(page, size, getSort(sort));

        /**
         * 전체 목록 조회
         */
        if (categoryId == null) {
            // 키워드 X
            if (keyword == null) {
                return getProductResponseSliceList(productRepository.findAllSlice(pageable));
            }
            // 키워드 O
            return getProductResponseSliceList(productRepository.findAllSliceByKeyword(keyword, pageable));
        }

        /**
         * 카테고리 별 목록 조회
         */
        validateCategoryId(categoryId); // 카테고리 유효성 검사

        // 키워드 X
        if (keyword == null) {
            return getProductResponseSliceList(productRepository.findAllSliceByCategoryId(categoryId, pageable));
        }
        // 키워드 O
        return getProductResponseSliceList(productRepository.findAllSliceByCategoryIdAndKeyword(categoryId, keyword, pageable));
    }

    // 상품 상세 조회
    public GetProductDetailResponse getProductDetail(Long productId) {
        // 상품
        Product product = findByProductId(productId);
        // 해당 상품의 옵션
        List<GetProductOptionResponse> options = getOptions(product);
        // 위시리스트 여부
        boolean isWishlist = getIsWishlist(productId);
        // 반환
        return GetProductDetailResponse.from(product, options, isWishlist);
    }

    // 정렬 객체
    private Sort getSort(Integer sort) {
        // 기본 순 (최신 순)
        if (sort == 0) {
            return Sort.by("id").descending();
        }
        // 리뷰 많은 순
        if (sort == 1) {
            return Sort.by("reviewCnt").descending();
        }
        // 평점 높은 순
        if (sort == 2) {
            return Sort.by("reviewAvg").descending();
        }
        // 가격 높은 순
        if (sort == 3) {
            return Sort.by("price").descending();
        }
        // 가격 낮은 순
        if (sort == 4) {
            return Sort.by("price").ascending();
        }
        // 예외
        throw new CustomException(SORT_NOT_FOUND);
    }

    private SliceList<GetProductResponse> getProductResponseSliceList(Slice<Product> products) {
        return SliceList.from(products.stream().map(GetProductResponse::from).collect(Collectors.toList()),
                products.getPageable(),
                products.hasNext());
    }

    private List<GetProductOptionResponse> getOptions(Product product) {
        return productOptionRepository.findAllByProduct(product)
                .stream().map(GetProductOptionResponse::from)
                .collect(Collectors.toList());
    }

    private Product findByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    private boolean getIsWishlist(Long productId) {
        Long consumerId = securityUtil.getConsumerIdOrNull();

        if (consumerId == null) {
            return false;
        }

        return wishlistRepository.findByConsumerIdAndProductId(consumerId, productId).isPresent();
    }

    private void validateCategoryId(Integer categoryId) {
        if (!productCategoryRepository.existsByIdAndDeletedAtIsNull(categoryId)) {
            throw new CustomException(PRODUCT_CATEGORY_NOT_FOUND);
        }
    }

}
