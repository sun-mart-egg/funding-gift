package com.d201.fundingift.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ToString
@Getter
public class PutReviewRequest {

    @NotNull(message = "star: 값이 null 이 아니어야 합니다.")
    @Range(min = 1, max = 5, message = "star: 값이 1에서 5 사이여야 합니다.")
    @Schema(description = "평점 (1~5)", example = "5")
    private Integer star;

    @Size(min = 10, max = 255, message = "content: 크기가 10에서 255 사이여야 합니다.")
    @Schema(description = "내용 (10~255자)", example = "우와아아아 너무 마음에 들어요")
    private String content;

}
