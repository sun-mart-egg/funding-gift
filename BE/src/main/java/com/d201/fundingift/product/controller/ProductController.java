package com.d201.fundingift.product.controller;

import com.d201.fundingift._common.response.ApiResponseDto;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.d201.fundingift._common.response.SuccessType.GET_CATEGORIES_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    public ApiResponseDto<?> getCategories() {
        return ResponseUtils.ok(productService.getCategories(), GET_CATEGORIES_SUCCESS);
    }

}
