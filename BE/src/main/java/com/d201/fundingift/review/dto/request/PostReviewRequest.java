package com.d201.fundingift.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ToString
@Getter
public class PostReviewRequest {

    @NotNull(message = "productId: 값이 null 이 아니어야 합니다.")
    private Long productId;

    @NotNull(message = "productOptionId: 값이 null 이 아니어야 합니다.")
    private Long productOptionId;

    @NotNull(message = "star: 값이 null 이 아니어야 합니다.")
    @Range(min = 1, max = 5, message = "star: 값이 1에서 5 사이여야 합니다.")
    private Integer star;

    @Size(min = 10, max = 255, message = "content: 크기가 10에서 255 사이여야 합니다.")
    private String content;

}
