package com.d201.fundingift.review.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.repository.ProductOptionRepository;
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

import static com.d201.fundingift._common.response.ErrorType.PRODUCT_OPTION_MISMATCH;
import static com.d201.fundingift._common.response.ErrorType.SORT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public List<GetReviewResponse> getReviews(Long productId, Long productOptionId, Integer page, Integer size, Integer sort) {
        Product product = findProductById(productId);
        Pageable pageable = PageRequest.of(page, size);

        // 상품 옵션 없음
        if (productOptionId == null) {
            // 최신 순
            if (sort == 0) {
                return findByProductOrderByCreatedAtDesc(product, pageable);
            }

            // 별점 높은 순
            if (sort == 1) {
                return findByProductOrderByStarDesc(product, pageable);
            }

            // 별점 낮은 순
            if (sort == 2) {
                return findByProductOrderByStarAsc(product, pageable);
            }

        }

        // 상품 옵션 있음
        ProductOption productOption = findProductOptionById(productOptionId);
        validateProductOption(productOption, productId); // 상품 옵션이 상품과 매칭되는지 검사

        // 최신 순
        if (sort == 0) {
            return findByProductAndOptionOrderByCreatedAtDesc(product, productOption, pageable);
        }

        // 별점 높은 순
        if (sort == 1) {
            return findByProductAndOptionOrderByStarDesc(product, productOption, pageable);
        }

        // 별점 낮은 순
        if (sort == 2) {
            return findByProductAndOptionOrderByStarAsc(product, productOption, pageable);
        }

        throw new CustomException(SORT_NOT_FOUND);
    }

    // 옵션 전체 + 최신 순
    private List<GetReviewResponse> findByProductOrderByCreatedAtDesc(Product product, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderByCreatedAtDesc(product, pageable)
                .stream().map(GetReviewResponse::from)
                .collect(Collectors.toList());
    }

    // 옵션 + 최신 순
    private List<GetReviewResponse> findByProductAndOptionOrderByCreatedAtDesc(Product product, ProductOption productOption, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderAndOptionByCreatedAtDesc(product, productOption, pageable)
                .stream().map(GetReviewResponse::from)
                .collect(Collectors.toList());
    }

    // 옵션 전체 + 별점 높은 순
    private List<GetReviewResponse> findByProductOrderByStarDesc(Product product, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderByStarDesc(product, pageable)
                .stream().map(GetReviewResponse::from)
                .collect(Collectors.toList());
    }

    // 옵션 + 별점 높은 순
    private List<GetReviewResponse> findByProductAndOptionOrderByStarDesc(Product product, ProductOption productOption, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderAndOptionByStarDesc(product, productOption, pageable)
                .stream().map(GetReviewResponse::from)
                .collect(Collectors.toList());
    }

    // 옵션 전체 + 별점 낮은 순
    private List<GetReviewResponse> findByProductOrderByStarAsc(Product product, Pageable pageable) {
        return reviewRepository.findAllSliceByProductOrderByStarAsc(product, pageable)
                .stream().map(GetReviewResponse::from)
                .collect(Collectors.toList());
    }

    // 옵션 + 별점 낮은 순
    private List<GetReviewResponse> findByProductAndOptionOrderByStarAsc(Product product, ProductOption productOption, Pageable pageable) {
        return reviewRepository.findAllSliceByProductAndOptionOrderByStarAsc(product, productOption, pageable)
                .stream().map(GetReviewResponse::from)
                .collect(Collectors.toList());
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

    private void validateProductOption(ProductOption productOption, Long productId) {
        if (productOption.getProduct().getId() != productId) {
            throw new CustomException(PRODUCT_OPTION_MISMATCH);
        }
    }

}
