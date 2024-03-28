package com.d201.fundingift.review.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.review.dto.request.PostReviewRequest;
import com.d201.fundingift.review.dto.request.PutReviewRequest;
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

import static com.d201.fundingift._common.response.SuccessType.*;

@Tag(name = "reviews", description = "리뷰 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성",
            description = """
                           `token` \n
                           리뷰를 생성합니다. \n
                           이미지는 최대 2장 가능합니다. (image1, image2)  \n
                           포스트맨으로 테스트해주세요. 테스트하는 방법은 https://fearless-texture-68a.notion.site/POSTMAN-74f33445c6824c189a4ddfad874b247b?pvs=4 참고해주세요.
                           - star: 값이 1에서 5 사이여야 합니다.
                           - content: 길이가 10에서 255 사이여야 합니다.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<Void> postReview(@RequestPart @Valid PostReviewRequest request,
                                            @Schema(example = "이미지 파일 1") @RequestPart(required = false) MultipartFile image1,
                                            @Schema(example = "이미지 파일 2") @RequestPart(required = false) MultipartFile image2) throws IOException {
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
             @Schema(description = "상품 옵션 ID", example = "1") @RequestParam(required = false, name = "product-option-id") Long productOptionId,
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

    @Operation(summary = "리뷰 수정 (보류)",
                description = "이미지 수정이 안 돼서 보류합니다.")
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @PutMapping("/{review-id}")
    public SuccessResponse<Void> updateReview(@PathVariable("review-id") Long reviewId,
                                              @RequestBody PutReviewRequest request) {
        log.info("[ReviewController.updateReview]");
        reviewService.updateReview(reviewId, request);
        return ResponseUtils.ok(UPDATE_REVIEW_SUCCESS);
    }

    @Operation(summary = "리뷰 삭제",
            description = """
                          리뷰를 삭제합니다. \n
                          Path Variable로 review-id 넣어주세요.
                          """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @DeleteMapping("/{review-id}")
    public SuccessResponse<Void> deleteReview(@PathVariable("review-id") Long reviewId) {
        log.info("[ReviewController.deleteReview]");
        reviewService.deleteReview(reviewId);
        return ResponseUtils.ok(DELETE_REVIEW_SUCCESS);
    }

}
