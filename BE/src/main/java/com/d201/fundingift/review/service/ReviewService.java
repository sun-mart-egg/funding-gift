package com.d201.fundingift.review.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.repository.ProductRepository;
import com.d201.fundingift.review.dto.response.GetReviewResponse;
import com.d201.fundingift.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.SORT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public List<GetReviewResponse> getReviews(Long productId, Integer page, Integer size, Integer sort) {
        Product product = findProductById(productId);
        Pageable pageable = PageRequest.of(page, size);

        // 최신 순
        if (sort == 0) {
            return reviewRepository.findAllSliceByProductOrderByCreatedAtDesc(product, pageable)
                    .stream().map(GetReviewResponse::from)
                    .collect(Collectors.toList());
        }

        // 추천 순
        if (sort == 1) {
            // 구현 예정
        }

        // 별점 높은 순
        if (sort == 2) {
            return reviewRepository.findAllSliceByProductOrderByStarDesc(product, pageable)
                    .stream().map(GetReviewResponse::from)
                    .collect(Collectors.toList());
        }

        // 별점 낮은 순
        if (sort == 3) {
            return reviewRepository.findAllSliceByProductOrderByStarAsc(product, pageable)
                    .stream().map(GetReviewResponse::from)
                    .collect(Collectors.toList());
        }

        throw new CustomException(SORT_NOT_FOUND);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_NOT_FOUND));
    }

}
