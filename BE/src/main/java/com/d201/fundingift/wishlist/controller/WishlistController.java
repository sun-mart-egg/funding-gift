package com.d201.fundingift.wishlist.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.wishlist.dto.request.WishlistRequest;
import com.d201.fundingift.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.d201.fundingift._common.response.SuccessType.CREATE_WISHLIST_SUCCESS;
import static com.d201.fundingift._common.response.SuccessType.DELETE_WISHLIST_SUCCESS;

@Tag(name = "wishlists", description = "위시리스트 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @Operation(summary = "위시리스트 추가",
            description = """
                           `token` \n
                           위시리스트에 상품(+ 상품 옵션)을 추가합니다.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "토큰이 없는 경우 / 잘못된 productId / 잘못된 productOptionId / 상품과 상품 옵션이 매칭 되지 않는 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("")
    public SuccessResponse<Void> createWishlistItem(@RequestBody WishlistRequest request) {
        log.info("[WishlistController.createWishlistItem]");
        wishlistService.createWishlistItem(request);
        return ResponseUtils.ok(CREATE_WISHLIST_SUCCESS);
    }

    @DeleteMapping("")
    @Operation(summary = "위시리스트 삭제",
            description = """
                           `token` \n
                           위시리스트에 상품(+ 상품 옵션)을 삭제합니다.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "토큰이 없는 경우 / 잘못된 productId / 잘못된 productOptionId / 상품과 상품 옵션이 매칭 되지 않는 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public SuccessResponse<Void> deleteWishlistItem(@RequestBody WishlistRequest request) {
        log.info("[WishlistController.deleteWishlistItem]");
        wishlistService.deleteWishlistItem(request);
        return ResponseUtils.ok(DELETE_WISHLIST_SUCCESS);
    }

}
