package com.d201.fundingift.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GetCategoryListResponse {

    private Long categoryId;
    private String categoryName;

    @Builder
    private GetCategoryListResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
