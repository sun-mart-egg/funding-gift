package com.d201.fundingift.review.dto.response;

import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
public class GetReviewResponse {

    private Long reviewId;
    private String optionName;
    private Boolean isMe;
    private String writerName;
    private String writerProfile;
    private Integer star;
    private String image1;
    private String image2;
    private String content;
    private String createdAt;
    private String updatedAt;

    @Builder
    private GetReviewResponse(Long reviewId, String optionName, Boolean isMe, String writerName, String writerProfile, Integer star, String image1, String image2, String content, String createdAt, String updatedAt) {
        this.reviewId = reviewId;
        this.optionName = optionName;
        this.isMe = isMe;
        this.writerName = writerName;
        this.writerProfile = writerProfile;
        this.star = star;
        this.image1 = image1;
        this.image2 = image2;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static GetReviewResponse from(Review review, Consumer consumer) {
        return builder()
                .reviewId(review.getId())
                .optionName(review.getProductOption().getName())
                .isMe(getIsMe(review.getConsumer(), consumer))
                .writerProfile(review.getConsumer().getProfileImageUrl())
                .writerName(review.getConsumer().getName())
                .star(review.getStar())
                .image1(review.getImage1())
                .image2(review.getImage2())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    private static boolean getIsMe(Consumer writer, Consumer viewer) {
        if (viewer == null) {
            return false;
        }
        return writer == viewer;
    }

}
