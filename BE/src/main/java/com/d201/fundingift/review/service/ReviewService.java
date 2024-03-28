package com.d201.fundingift.review.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.S3Uploader;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.review.dto.request.PostReviewRequest;
import com.d201.fundingift.review.dto.request.PutReviewRequest;
import com.d201.fundingift.review.dto.response.GetReviewResponse;
import com.d201.fundingift.review.entity.Review;
import com.d201.fundingift.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final S3Uploader s3Uploader;
    private final SecurityUtil securityUtil;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void postReview(PostReviewRequest request, MultipartFile image1, MultipartFile image2) throws IOException {
        log.info("postReview : {}", request.toString());

        Consumer consumer = getConsumer();
        Product product = findProductById(request.getProductId());
        ProductOption productOption = findProductOptionById(request.getProductOptionId());

        // 상품 옵션이 상품과 매칭되는지 검사
        validateProductOption(product, productOption);

        // S3 이미지 업로드
        String imageUrl1 = null, imageUrl2 = null;
        if (image1 != null && !image1.isEmpty()) {
            imageUrl1 = s3Uploader.upload(image1);
        }
        if (image2 != null && !image2.isEmpty()) {
            imageUrl2 = s3Uploader.upload(image2);
        }

        // 리뷰 생성
        reviewRepository.save(Review.from(request, imageUrl1, imageUrl2, product, productOption, consumer));

        // 리뷰 개수 추가
        product.insertReview(request.getStar());
    }

    public SliceList<GetReviewResponse> getReviews(Long productId, Long productOptionId, Integer page, Integer size, Integer sort) {
        // 상품, 상품 옵션
        Product product = findProductById(productId);
        ProductOption productOption = findProductOptionById(productOptionId);
        // 상품 옵션이 상품과 매칭되는지 검사
        validateProductOption(product, productOption);

        // 페이징 객체
        Pageable pageable = PageRequest.of(page, size, getSort(sort));

        return getReviewResponseSliceList(reviewRepository.findAllSliceByProductAndOption(product, productOption, pageable),
                                            getConsumerOrNull());
    }

    @Transactional
    public void updateReview(Long reviewId, PutReviewRequest request) {
        log.info("updateReview : {}", reviewId);
        log.info(request.toString());

        Consumer consumer = getConsumer();
        Review review = findReviewById(reviewId);
        validateReviewAndConsumer(review, consumer); // 작성자 일치하는지 검사

        // 상품 - 별점 상태 업데이트
        review.getProduct().updateReview(review.getStar(), request.getStar());
        // 수정하기
        review.update(request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        log.info("deleteReview: {}", reviewId);

        Consumer consumer = getConsumer();
        Review review = findReviewById(reviewId);
        validateReviewAndConsumer(review, consumer); // 작성자 일치하는지 검사

        // 상품 - 별점 상태 업데이트
        review.getProduct().deleteReview(review.getStar());
        // 삭제하기
        reviewRepository.delete(review);
    }

    private Sort getSort(Integer sort) {
        // 최신 순
        if (sort == 0) {
            return Sort.by("createdAt").descending();
        }
        // 별점 높은 순
        if (sort == 1) {
            return Sort.by("star").descending();
        }
        // 별점 낮은 순
        if (sort == 2) {
            return Sort.by("star").ascending();
        }
        // 예외
        throw new CustomException(SORT_NOT_FOUND);
    }

    private SliceList<GetReviewResponse> getReviewResponseSliceList(Slice<Review> reviews, Consumer consumer) {
        return SliceList.from(reviews.stream().map(r -> GetReviewResponse.from(r, consumer)).collect(Collectors.toList()),
                reviews.getPageable(),
                reviews.hasNext());
    }

    // 상품
    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    // 상품 옵션
    private ProductOption findProductOptionById(Long productOptionId) {
        return productOptionRepository.findByIdAndStatusIsNotInactive(productOptionId)
                .orElseThrow(() -> new CustomException(PRODUCT_OPTION_NOT_FOUND));
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
    }

    private void validateProductOption(Product product, ProductOption productOption) {
        if (productOption.getProduct().getId() != product.getId()) {
            throw new CustomException(PRODUCT_OPTION_MISMATCH);
        }
    }

    private void validateReviewAndConsumer(Review review, Consumer consumer) {
        if (!review.getConsumer().equals(consumer)) {
            throw new CustomException(USER_UNAUTHORIZED);
        }
    }

    private Consumer getConsumer() {
        return securityUtil.getConsumer();
    }

    private Consumer getConsumerOrNull() {
        return securityUtil.getConsumerOrNull();
    }

}
