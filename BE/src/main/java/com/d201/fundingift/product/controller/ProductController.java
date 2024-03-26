package com.d201.fundingift.product.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.product.dto.response.GetProductCategoryResponse;
import com.d201.fundingift.product.dto.response.GetProductDetailResponse;
import com.d201.fundingift.product.dto.response.GetProductResponse;
import com.d201.fundingift.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.d201.fundingift._common.response.SuccessType.*;

@Tag(name = "products", description = "상품 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 카테고리 목록 조회",
            description = "상품 카테고리 목록을 조회합니다.")
    @ApiResponse(responseCode = "200",
                description = "성공",
                useReturnTypeSchema = true)
    @GetMapping("/categories")
    public SuccessResponse<List<GetProductCategoryResponse>> getCategories() {
        log.info("[ProductController.getCategories]");
        return ResponseUtils.ok(productService.getCategories(), GET_PRODUCT_CATEGORIES_SUCCESS);
    }

    @Operation(summary = "카테고리 별 상품 목록 조회",
            description = "카테고리 별 상품 목록을 조회합니다. Query Parameter로 category-id, page, size, sort 넣어주세요.")
    @ApiResponse(responseCode = "200",
                description = "성공",
                useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
                description = "잘못된 category-id / 잘못된 sort",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("")
    public SuccessResponse<SliceList<GetProductResponse>> getProductsByCategory
            (@Schema(description = "카테고리 ID", example = "1") @RequestParam(required = true, name = "category-id") Integer categoryId,
             @Schema(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(required = true, name = "page") Integer page,
             @Schema(description = "한 페이지에 불러올 데이터의 개수", example = "10") @RequestParam(required = true, name = "size") Integer size,
             @Schema(description = """
                                    정렬 조건
                                    - 0: 기본 순
                                    - 1: 리뷰 많은 순
                                    - 2: 평점 높은 순
                                    - 3: 가격 높은 순
                                    - 4: 가격 낮은 순
                                    """, example = "0") @RequestParam(required = true, name = "sort") Integer sort) {
        log.info("[ProductController.getProductsByCategoryId]");
        return ResponseUtils.ok(productService.getProductsByCategoryId(categoryId, page, size, sort), GET_PRODUCTS_BY_CATEGORY_SUCCESS);
    }

    @GetMapping("/search")
    public SuccessResponse<SliceList<GetProductResponse>> getProductsByKeyword(@RequestParam(required = true, name = "keyword") String keyword,
                                                                         @RequestParam(required = true, name = "page") Integer page,
                                                                         @RequestParam(required = true, name = "size") Integer size,
                                                                         @RequestParam(required = true, name = "sort") Integer sort) {
        log.info("[ProductController.getProductsByKeyword]");
        return ResponseUtils.ok(productService.getProductsByKeyword(keyword, page, size, sort), GET_PRODUCTS_BY_KEYWORD_SUCCESS);
    }

    // 토큰 검사 추가 필요 (위시리스트 여부 확인)
    @Operation(summary = "상품 상세 조회",
            description = "상품의 디테일을 조회합니다. Path Variable로 product-id 넣어주세요.")
    @ApiResponse(responseCode = "200",
                description = "성공",
                useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
                description = "잘못된 product-id",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{product-id}")
    public SuccessResponse<GetProductDetailResponse> getProductDetail
            (@Schema(description = "상품 ID", example = "1") @PathVariable(required = true, name = "product-id") Long productId) {
        log.info("[ProductController.getProductDetail]");
        return ResponseUtils.ok(productService.getProductDetail(productId), GET_PRODUCT_DETAIL_SUCCESS);
    }

}
