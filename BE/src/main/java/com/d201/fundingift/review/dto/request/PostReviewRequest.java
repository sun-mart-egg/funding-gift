package com.d201.fundingift.review.dto.request;

import lombok.Getter;

@Getter
public class PostReviewRequest {

    private Long productId;
    private Long productOptionId;
    private Integer star;
    private String content;
    private String image1;
    private String image2;

}
