package com.d201.fundingift.product.dto.response;

import com.d201.fundingift.product.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Schema(name = "GetProductCategoryResponse", description = "상품 카테고리 목록 조회 응답")
public class GetProductCategoryResponse {

    @Schema(description = "카테고리 ID", example = "1")
    private int categoryId;

    @Schema(description = "카테고리 이름", example = "패션")
    private String categoryName;

    @Builder
    private GetProductCategoryResponse(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static GetProductCategoryResponse from(ProductCategory productCategory) {
        return builder()
                .categoryId(productCategory.getId())
                .categoryName(productCategory.getName())
                .build();
    }

}
