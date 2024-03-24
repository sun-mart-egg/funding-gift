package com.d201.fundingift.review.dto.response;

import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class GetReviewResponse {

    private Long reviewId;
    private String optionName;
    private String writer;
    private Integer star;
    private String image1;
    private String image2;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private GetReviewResponse(Long reviewId, String optionName, String writer, Integer star, String image1, String image2, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reviewId = reviewId;
        this.optionName = optionName;
        this.writer = writer;
        this.star = star;
        this.image1 = image1;
        this.image2 = image2;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static GetReviewResponse from(Review review) {
        return builder()
                .reviewId(review.getId())
                .optionName(review.getProductOption().getName())
                .writer(review.getConsumer().getName())
                .star(review.getStar())
                .image1(review.getImage1())
                .image2(review.getImage2())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

}
