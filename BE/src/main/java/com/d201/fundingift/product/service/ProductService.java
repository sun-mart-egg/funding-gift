package com.d201.fundingift.product.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift.product.dto.response.GetProductCategoryResponse;
import com.d201.fundingift.product.dto.response.GetProductDetailResponse;
import com.d201.fundingift.product.dto.response.GetProductOptionResponse;
import com.d201.fundingift.product.dto.response.GetProductResponse;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.repository.ProductCategoryRepository;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    // 카테고리 리스트 조회
    public List<GetProductCategoryResponse> getCategories() {
        return productCategoryRepository.findAllByDeletedAtIsNull()
                .stream().map(GetProductCategoryResponse::from)
                .collect(Collectors.toList());
    }

    // 카테고리 별 상품 리스트 조회
    public List<GetProductResponse> getProducts(Integer categoryId, Integer page, Integer size, Integer sort) {
        validateCategoryId(categoryId);
        Pageable pageable = PageRequest.of(page, size);

        // 기본 순
        if (sort == 0) {
            return productRepository.findAllSliceByProductCategoryId(categoryId, pageable)
                    .stream().map(GetProductResponse::from)
                    .collect(Collectors.toList());
        }

        // 리뷰 많은 순
        if (sort == 1) {
            return productRepository.findAllSliceByProductCategoryIdOrderByReviewCntDesc(categoryId, pageable)
                    .stream().map(GetProductResponse::from)
                    .collect(Collectors.toList());
        }

        // 평점 높은 순
        if (sort == 2) {
            return productRepository.findAllSliceByProductCategoryIdOrderByReviewAvgDesc(categoryId, pageable)
                    .stream().map(GetProductResponse::from)
                    .collect(Collectors.toList());
        }

        // 가격 높은 순
        if (sort == 3) {
            return productRepository.findAllSliceByProductCategoryIdOrderByPriceDesc(categoryId, pageable)
                    .stream().map(GetProductResponse::from)
                    .collect(Collectors.toList());
        }

        // 가격 낮은 순
        if (sort == 4) {
            return productRepository.findAllSliceByProductCategoryIdOrderByPriceAsc(categoryId, pageable)
                    .stream().map(GetProductResponse::from)
                    .collect(Collectors.toList());
        }

        throw new CustomException(SORT_NOT_FOUND);
    }

    // 상품 상세 조회
    public GetProductDetailResponse getProductDetail(Long productId) {
        // 상품
        Product product = findByProductId(productId);
        // 해당 상품의 옵션
        List<GetProductOptionResponse> options = getOptions(product);
        // 반환
        return GetProductDetailResponse.from(product, options);
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

    private void validateCategoryId(Integer categoryId) {
        if (!productCategoryRepository.existsByIdAndDeletedAtIsNull(categoryId)) {
            throw new CustomException(CATEGORY_NOT_FOUND);
        }
    }

}
