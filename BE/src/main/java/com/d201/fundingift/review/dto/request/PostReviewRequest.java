package com.d201.fundingift.review.dto.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
public class PostReviewRequest {

    private Long productId;
    private Long productOptionId;
    private Integer star;
    private String content;
    private MultipartFile image1;
    private MultipartFile image2;

}
