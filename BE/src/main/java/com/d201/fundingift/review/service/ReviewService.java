package com.d201.fundingift.review.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.review.dto.request.PostReviewRequest;
import com.d201.fundingift.review.dto.response.GetReviewResponse;
import com.d201.fundingift.review.entity.Review;
import com.d201.fundingift.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.PRODUCT_OPTION_MISMATCH;
import static com.d201.fundingift._common.response.ErrorType.SORT_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final SecurityUtil securityUtil;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void postReview(PostReviewRequest request) {
        Consumer consumer = getConsumer();
        Product product = findProductById(request.getProductId());
        ProductOption productOption = findProductOptionById(request.getProductOptionId());

        // 상품 옵션이 상품과 매칭되는지 검사
        validateProductOption(product, productOption);

        // 리뷰 생성
        reviewRepository.save(Review.from(request, product, productOption, consumer));

        // 리뷰 개수 추가
        product.insertReview(request.getStar());
    }

    public SliceList<GetReviewResponse> getReviews(Long productId, Long productOptionId, Integer page, Integer size, Integer sort) {
        Product product = findProductById(productId);
        Consumer consumer = getConsumerOrNull();
        Pageable pageable = PageRequest.of(page, size);

        // 상품 옵션 없음
        if (productOptionId == null) {
            // 최신 순
            if (sort == 0) {
                return getReviewResponseSliceList(findByProductOrderByCreatedAtDesc(product, pageable), consumer);
            }

            // 별점 높은 순
            if (sort == 1) {
                return getReviewResponseSliceList(findByProductOrderByStarDesc(product, pageable), consumer);
            }

            // 별점 낮은 순
            if (sort == 2) {
                return getReviewResponseSliceList(findByProductOrderByStarAsc(product, pageable), consumer);
            }

        }

        // 상품 옵션 있음
        ProductOption productOption = findProductOptionById(productOptionId);
        validateProductOption(product, productOption); // 상품 옵션이 상품과 매칭되는지 검사

        // 최신 순
        if (sort == 0) {
            return getReviewResponseSliceList(findByProductAndOptionOrderByCreatedAtDesc(product, productOption, pageable), consumer);
        }

        // 별점 높은 순
        if (sort == 1) {
            return getReviewResponseSliceList(findByProductAndOptionOrderByStarDesc(product, productOption, pageable), consumer);
        }

        // 별점 낮은 순
        if (sort == 2) {
            return getReviewResponseSliceList(findByProductAndOptionOrderByStarAsc(product, productOption, pageable), consumer);
        }

        throw new CustomException(SORT_NOT_FOUND);
    }

    // 옵션 전체 + 최신 순
    private Slice<Review> findByProductOrderByCreatedAtDesc(Product product, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderByCreatedAtDesc(product, pageable);
    }

    // 옵션 + 최신 순
    private Slice<Review> findByProductAndOptionOrderByCreatedAtDesc(Product product, ProductOption productOption, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderAndOptionByCreatedAtDesc(product, productOption, pageable);
    }

    // 옵션 전체 + 별점 높은 순
    private Slice<Review> findByProductOrderByStarDesc(Product product, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderByStarDesc(product, pageable);
    }

    // 옵션 + 별점 높은 순
    private Slice<Review> findByProductAndOptionOrderByStarDesc(Product product, ProductOption productOption, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderAndOptionByStarDesc(product, productOption, pageable);
    }

    // 옵션 전체 + 별점 낮은 순
    private Slice<Review> findByProductOrderByStarAsc(Product product, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderByStarAsc(product, pageable);
    }

    // 옵션 + 별점 낮은 순
    private Slice<Review> findByProductAndOptionOrderByStarAsc(Product product, ProductOption productOption, Pageable pageable) {
        return reviewRepository.findAllSliceByProductAndOptionOrderByStarAsc(product, productOption, pageable);
    }

    private SliceList<GetReviewResponse> getReviewResponseSliceList(Slice<Review> reviews, Consumer consumer) {
        return SliceList.from(reviews.stream().map(r -> GetReviewResponse.from(r, consumer)).collect(Collectors.toList()),
                reviews.getPageable(),
                reviews.hasNext());
    }

    // 상품
    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_NOT_FOUND));
    }

    // 상품 옵션
    private ProductOption findProductOptionById(Long productOptionId) {
        return productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_OPTION_NOT_FOUND));
    }

    private void validateProductOption(Product product, ProductOption productOption) {
        if (productOption.getProduct().getId() != product.getId()) {
            throw new CustomException(PRODUCT_OPTION_MISMATCH);
        }
    }

    private Consumer getConsumer() {
        return securityUtil.getConsumer();
    }

    private Consumer getConsumerOrNull() {
        return securityUtil.getConsumerOrNull();
    }

}
