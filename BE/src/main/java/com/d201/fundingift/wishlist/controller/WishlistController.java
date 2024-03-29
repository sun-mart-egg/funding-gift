package com.d201.fundingift.wishlist.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.wishlist.dto.request.WishlistRequest;
import com.d201.fundingift.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.d201.fundingift._common.response.SuccessType.CREATE_WISHLIST_SUCCESS;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("")
    public SuccessResponse<Void> createWishlistItem(@RequestBody WishlistRequest request) {
        log.info("[WishlistController.createWishlistItem]");
        wishlistService.createWishlistItem(request);
        return ResponseUtils.ok(CREATE_WISHLIST_SUCCESS);
    }
}
