package com.d201.fundingift.review.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.review.dto.response.GetReviewResponse;
import com.d201.fundingift.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.d201.fundingift._common.response.SuccessType.GET_REVIEWS_BY_PRODUCT_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public SuccessResponse<List<GetReviewResponse>> getReviews(@RequestParam(required = true, name = "product-id") Long productId,
                                                               @RequestParam(required = true, name = "page") Integer page,
                                                               @RequestParam(required = true, name = "size") Integer size,
                                                               @RequestParam(required = true, name = "sort") Integer sort) {
        log.info("[ReviewController.getReviews]");
        return ResponseUtils.ok(reviewService.getReviews(productId, page, size, sort), GET_REVIEWS_BY_PRODUCT_SUCCESS);
    }

}
