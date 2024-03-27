package com.d201.fundingift.product.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.SliceList;
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
import org.springframework.data.domain.Slice;
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
    public SliceList<GetProductResponse> getProductsByCategoryId(Integer categoryId, Integer page, Integer size, Integer sort) {
        Pageable pageable = PageRequest.of(page, size);

        if (categoryId == null) {
            // 기본 순
            if (sort == 0) {
                return getProductResponseSliceList(findAll(pageable));
            }

            // 리뷰 많은 순
            if (sort == 1) {
                return getProductResponseSliceList(findAllOrderByReviewCntDesc(pageable));
            }

            // 평점 높은 순
            if (sort == 2) {
                return getProductResponseSliceList(findAllOrderByReviewAvgDesc(pageable));
            }

            // 가격 높은 순
            if (sort == 3) {
                return getProductResponseSliceList(findAllOrderByPriceDesc(pageable));
            }

            // 가격 낮은 순
            if (sort == 4) {
                return getProductResponseSliceList(findAllOrderByPriceAsc(pageable));
            }

            throw new CustomException(SORT_NOT_FOUND);
        }

        validateCategoryId(categoryId);

        // 기본 순
        if (sort == 0) {
            return getProductResponseSliceList(findByCategoryId(categoryId, pageable));
        }

        // 리뷰 많은 순
        if (sort == 1) {
            return getProductResponseSliceList(findByCategoryIdOrderByReviewCntDesc(categoryId, pageable));
        }

        // 평점 높은 순
        if (sort == 2) {
            return getProductResponseSliceList(findByCategoryIdOrderByReviewAvgDesc(categoryId, pageable));
        }

        // 가격 높은 순
        if (sort == 3) {
            return getProductResponseSliceList(findByCategoryIdOrderByPriceDesc(categoryId, pageable));
        }

        // 가격 낮은 순
        if (sort == 4) {
            return getProductResponseSliceList(findByCategoryIdOrderByPriceAsc(categoryId, pageable));
        }

        throw new CustomException(SORT_NOT_FOUND);
    }

    // 검색어 별 상품 리스트 조회
    public SliceList<GetProductResponse> getProductsByKeyword(String keyword, Integer page, Integer size, Integer sort) {
        Pageable pageable = PageRequest.of(page, size);

        // 기본 순
        if (sort == 0) {
            return getProductResponseSliceList(findByKeyword(keyword, pageable));
        }

        // 리뷰 많은 순
        if (sort == 1) {
            return getProductResponseSliceList(findByKeywordOrderByReviewCntDesc(keyword, pageable));
        }

        // 평점 높은 순
        if (sort == 2) {
            return getProductResponseSliceList(findByKeywordOrderByReviewAvgDesc(keyword, pageable));
        }

        // 가격 높은 순
        if (sort == 3) {
            return getProductResponseSliceList(findByKeywordOrderByPriceDesc(keyword, pageable));
        }

        // 가격 낮은 순
        if (sort == 4) {
            return getProductResponseSliceList(findByKeywordOrderByPriceAsc(keyword, pageable));
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

    private Slice<Product> findAll(Pageable pageable) {
        return productRepository.findAllSlice(pageable);
    }

    private Slice<Product> findAllOrderByReviewCntDesc(Pageable pageable) {
        return productRepository.findAllSliceOrderByReviewCntDesc(pageable);
    }

    private Slice<Product> findAllOrderByReviewAvgDesc(Pageable pageable) {
        return productRepository.findAllSliceOrderByReviewAvgDesc(pageable);
    }

    private Slice<Product> findAllOrderByPriceDesc(Pageable pageable) {
        return productRepository.findAllSliceOrderByPriceDesc(pageable);
    }

    private Slice<Product> findAllOrderByPriceAsc(Pageable pageable) {
        return productRepository.findAllSliceOrderByPriceAsc(pageable);
    }

    private Slice<Product> findByCategoryId(Integer categoryId, Pageable pageable) {
        return productRepository.findAllSliceByProductCategoryId(categoryId, pageable);
    }

    private Slice<Product> findByCategoryIdOrderByReviewCntDesc(Integer categoryId, Pageable pageable) {
        return productRepository.findAllSliceByProductCategoryIdOrderByReviewCntDesc(categoryId, pageable);
    }

    private Slice<Product> findByCategoryIdOrderByReviewAvgDesc(Integer categoryId, Pageable pageable) {
        return productRepository.findAllSliceByProductCategoryIdOrderByReviewAvgDesc(categoryId, pageable);
    }

    private Slice<Product> findByCategoryIdOrderByPriceDesc(Integer categoryId, Pageable pageable) {
        return productRepository.findAllSliceByProductCategoryIdOrderByPriceDesc(categoryId, pageable);
    }

    private Slice<Product> findByCategoryIdOrderByPriceAsc(Integer categoryId, Pageable pageable) {
        return productRepository.findAllSliceByProductCategoryIdOrderByPriceAsc(categoryId, pageable);
    }

    private Slice<Product> findByKeyword(String keyword, Pageable pageable) {
        return productRepository.findAllSliceByKeyword(keyword, pageable);
    }

    private Slice<Product> findByKeywordOrderByReviewAvgDesc(String keyword, Pageable pageable) {
        return productRepository.findAllSliceByKeywordOrderByReviewAvgDesc(keyword, pageable);
    }

    private Slice<Product> findByKeywordOrderByReviewCntDesc(String keyword, Pageable pageable) {
        return productRepository.findAllSliceByKeywordOrderByReviewCntDesc(keyword, pageable);
    }

    private Slice<Product> findByKeywordOrderByPriceDesc(String keyword, Pageable pageable) {
        return productRepository.findAllSliceByKeywordOrderByPriceDesc(keyword, pageable);
    }

    private Slice<Product> findByKeywordOrderByPriceAsc(String keyword, Pageable pageable) {
        return productRepository.findAllSliceByKeywordOrderByPriceAsc(keyword, pageable);
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

    private void validateCategoryId(Integer categoryId) {
        if (!productCategoryRepository.existsByIdAndDeletedAtIsNull(categoryId)) {
            throw new CustomException(PRODUCT_CATEGORY_NOT_FOUND);
        }
    }

}
