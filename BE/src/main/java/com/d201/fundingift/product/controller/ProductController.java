package com.d201.fundingift.product.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.product.dto.response.GetProductCategoryResponse;
import com.d201.fundingift.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
