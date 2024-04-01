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

    @Operation(summary = "상품 목록 조회",
            description = """
                           상품 목록을 조회합니다. \n
                           Query Parameter로 category-id, keyword, page, size, sort 넣어주세요. \n
                           category-id가 없으면 전체 목록을 조회합니다. \n
                           keyword가 있으면 해당 검색어 목록을 조회합니다. \n
                           결과로 data, page, size, hasNext를 반환합니다.
                           - data: 응답 데이터
                           - page: 현재 페이지 번호
                           - size: 현재 데이터 개수
                           - hasNext: 다음 페이지 존재 여부
                           """)
    @ApiResponse(responseCode = "200",
                description = "성공",
                useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
                description = "잘못된 category-id / 잘못된 sort",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("")
    public SuccessResponse<SliceList<GetProductResponse>> getProducts
            (@Schema(description = "카테고리 ID, 없으면 전체 목록을 조회합니다.", example = "1") @RequestParam(required = false, name = "category-id") Integer categoryId,
             @Schema(description = "검색 키워드, 없으면 키워드 없이 조회합니다.", example = "반지") @RequestParam(required = false, name = "keyword") String keyword,
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
        return ResponseUtils.ok(productService.getProducts(categoryId, keyword, page, size, sort), GET_PRODUCTS_SUCCESS);
    }

    @Operation(summary = "추천 상품 목록 조회",
            description = """
                           추천 상품 목록을 조회합니다. (랭킹) \n
                           Query Parameter로 page, size 넣어주세요. \n
                           결과로 data, page, size, hasNext를 반환합니다.
                           - data: 응답 데이터
                           - page: 현재 페이지 번호
                           - size: 현재 데이터 개수
                           - hasNext: 다음 페이지 존재 여부
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @GetMapping("/rank")
    public SuccessResponse<SliceList<GetProductResponse>> getProductsRank(
            @Schema(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam Integer page,
            @Schema(description = "한 페이지에 불러올 데이터의 개수", example = "10") @RequestParam Integer size) {
        log.info("[ProductController.getProductsRank]");
        return ResponseUtils.ok(productService.getProductsRank(page, size), GET_PRODUCTS_RANK_SUCCESS);
    }

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
