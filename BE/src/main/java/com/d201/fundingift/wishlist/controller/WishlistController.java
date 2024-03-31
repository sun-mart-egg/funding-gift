package com.d201.fundingift.wishlist.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.wishlist.dto.WishlistDto;
import com.d201.fundingift.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.d201.fundingift._common.response.SuccessType.*;

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
                           위시리스트에 상품을 추가합니다.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "토큰이 없는 경우 / 잘못된 productId / 위시리스트에 이미 추가된 상품일 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("")
    public SuccessResponse<Void> createWishlistItem(@RequestBody WishlistDto request) {
        log.info("[WishlistController.createWishlistItem]");
        wishlistService.createWishlistItem(request);
        return ResponseUtils.ok(CREATE_WISHLIST_SUCCESS);
    }

    @Operation(summary = "위시리스트 삭제",
            description = """
                           `token` \n
                           위시리스트에서 상품을 삭제합니다.
                           """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "토큰이 없는 경우 / 잘못된 productId / 위시리스트에 이미 없는 상품일 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("")
    public SuccessResponse<Void> deleteWishlistItem(@RequestBody WishlistDto request) {
        log.info("[WishlistController.deleteWishlistItem]");
        wishlistService.deleteWishlistItem(request);
        return ResponseUtils.ok(DELETE_WISHLIST_SUCCESS);
    }

//    @Operation(summary = "위시리스트 조회",
//            description = """
//                           `token` \n
//                           유저의 위시리스트 목록을 조회합니다. \n
//                           Query Parameter로 page, size 넣어주세요.
//                           결과로 data, page, size, hasNext를 반환합니다.
//                           - data: 응답 데이터
//                           - page: 현재 페이지 번호
//                           - size: 현재 데이터 개수
//                           - hasNext: 다음 페이지 존재 여부
//                           """)
//    @ApiResponse(responseCode = "200",
//            description = "성공",
//            useReturnTypeSchema = true)
//    @ApiResponse(responseCode = "400",
//            description = "토큰이 없는 경우",
//            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//    @GetMapping("")
//    public SuccessResponse<SliceList<WishlistDto>> getWishlists
//                (@Schema(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam Integer page,
//                 @Schema(description = "한 페이지에 불러올 데이터의 개수", example = "10") @RequestParam Integer size) {
//        log.info("[WishlistController.getWishlists]");
//        return ResponseUtils.ok(wishlistService.getWishlists(page, size), GET_WISHLISTS_SUCCESS);
//    }
}
