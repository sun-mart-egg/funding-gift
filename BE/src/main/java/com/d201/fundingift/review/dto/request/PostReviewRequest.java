package com.d201.fundingift.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ToString
@Getter
@Schema(name = "PostReviewRequest", description = "리뷰 생성 요청")
public class PostReviewRequest {

    @NotNull(message = "productId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @NotNull(message = "productOptionId: 값이 null 이 아니어야 합니다.")
    @Schema(description = "상품 옵션 ID", example = "1")
    private Long productOptionId;

    @NotNull(message = "star: 값이 null 이 아니어야 합니다.")
    @Range(min = 1, max = 5, message = "star: 값이 1에서 5 사이여야 합니다.")
    @Schema(description = "평점 (1~5)", example = "5")
    private Integer star;

    @NotBlank(message = "content: 값이 비어있지 않아야 합니다.")
    @Size(min = 10, max = 255, message = "content: 크기가 10에서 255 사이여야 합니다.")
    @Schema(description = "내용 (10~255자)", example = "우와아아아 너무 마음에 들어요")
    private String content;

}
