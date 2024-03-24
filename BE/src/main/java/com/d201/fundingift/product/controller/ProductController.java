package com.d201.fundingift.product.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.product.dto.response.GetProductCategoryResponse;
import com.d201.fundingift.product.dto.response.GetProductResponse;
import com.d201.fundingift.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.d201.fundingift._common.response.SuccessType.GET_PRODUCTS_BY_CATEGORY_SUCCESS;
import static com.d201.fundingift._common.response.SuccessType.GET_PRODUCT_CATEGORIES_SUCCESS;

@Tag(name = "products", description = "상품 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 카테고리 목록 조회",
            description = "상품 카테고리 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true)
    })
    @GetMapping("/categories")
    public SuccessResponse<List<GetProductCategoryResponse>> getCategories() {
        log.info("[ProductController.getCategories]");
        return ResponseUtils.ok(productService.getCategories(), GET_PRODUCT_CATEGORIES_SUCCESS);
    }

    @Operation(summary = "카테고리 별 상품 목록 조회",
            description = "카테고리 별 상품 목록을 조회합니다. Query Parameter로 category-id, page, size, sort 넣어주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "잘못된 category-id / 잘못된 sort",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("")
    public SuccessResponse<List<GetProductResponse>> getProducts
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
        log.info("[ProductController.getProducts]");
        return ResponseUtils.ok(productService.getProducts(categoryId, page, size, sort), GET_PRODUCTS_BY_CATEGORY_SUCCESS);
    }

}
