package com.d201.fundingift.review.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.review.dto.request.PostReviewRequest;
import com.d201.fundingift.review.dto.response.GetReviewResponse;
import com.d201.fundingift.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.d201.fundingift._common.response.SuccessType.CREATE_REVIEW_SUCCESS;
import static com.d201.fundingift._common.response.SuccessType.GET_REVIEWS_BY_PRODUCT_SUCCESS;

@Tag(name = "reviews", description = "리뷰 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<Void> postReview(@RequestPart @Valid PostReviewRequest request,
                                            @RequestPart(required = false) MultipartFile image1,
                                            @RequestPart(required = false) MultipartFile image2) throws IOException {
        log.info("[ReviewController.postReview]");
        reviewService.postReview(request, image1, image2);
        return ResponseUtils.ok(CREATE_REVIEW_SUCCESS);
    }

    @Operation(summary = "상품 별 리뷰 목록 조회",
            description = """
                           상품 별 리뷰 목록을 조회합니다. \n
                           Query Parameter로 product-id, product-option-id, page, size, sort 넣어주세요. \n
                           product-option-id가 없으면 전체 옵션을 조회합니다. \n
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
            description = "잘못된 product-id / 잘못된 sort",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("")
    public SuccessResponse<SliceList<GetReviewResponse>> getReviews
            (@Schema(description = "상품 ID", example = "1") @RequestParam(required = true, name = "product-id") Long productId,
             @Schema(description = "상품 옵션 ID, 없으면 전체 옵션 데이터를 반환합니다.", example = "1") @RequestParam(required = false, name = "product-option-id") Long productOptionId,
             @Schema(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(required = true, name = "page") Integer page,
             @Schema(description = "한 페이지에 불러올 데이터의 개수", example = "10") @RequestParam(required = true, name = "size") Integer size,
             @Schema(description = """
                                    정렬 조건
                                    - 0: 최신 순
                                    - 1: 별점 높은 순
                                    - 2: 별점 낮은 순
                                    """, example = "0") @RequestParam(required = true, name = "sort") Integer sort) {
        log.info("[ReviewController.getReviews]");
        return ResponseUtils.ok(reviewService.getReviews(productId, productOptionId, page, size, sort), GET_REVIEWS_BY_PRODUCT_SUCCESS);
    }

}
