package com.d201.fundingift.funding.dto.response;

import com.d201.fundingift.funding.entity.AnniversaryCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAnniversaryCategoryResponse {

    @Schema(description = "기념일 고유번호", example = "1")
    private Integer anniversaryCategoryId;

    @Schema(description = "기념일 이름", example = "생일")
    private String anniversaryCategoryName;

    @Builder
    private GetAnniversaryCategoryResponse(Integer anniversaryCategoryId, String anniversaryCategoryName) {
        this.anniversaryCategoryId = anniversaryCategoryId;
        this.anniversaryCategoryName = anniversaryCategoryName;
    }

    public static GetAnniversaryCategoryResponse from(AnniversaryCategory anniversaryCategory) {
        return builder()
                .anniversaryCategoryId(anniversaryCategory.getId())
                .anniversaryCategoryName(anniversaryCategory.getName())
                .build();
    }
}
