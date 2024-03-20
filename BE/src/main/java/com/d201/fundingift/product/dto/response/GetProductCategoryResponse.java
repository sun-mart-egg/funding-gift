package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GetCategoryResponse {

    private int categoryId;
    private String categoryName;

    @Builder
    private GetCategoryResponse(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static GetCategoryResponse from(ProductCategory productCategory) {
        return builder()
                .categoryId(productCategory.getId())
                .categoryName(productCategory.getName())
                .build();
    }

}
