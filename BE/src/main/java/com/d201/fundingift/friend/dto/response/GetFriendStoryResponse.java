package com.d201.fundingift.friend.dto.response;

import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.funding.entity.Funding;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class GetFriendStoryResponse implements Comparable<GetFriendStoryResponse>{

    @Schema(description = "친구 아이디", example = "43")
    private Long consumerId;

    @Schema(description = "친구 이름", example = "수비니")
    private String name;

    @Schema(description = "친구 프로필 사진", example = "image url")
    private String profileImageUrl;

    @Schema(description = "친구 펀딩 목록 중 진행중이고 시작일이 가장 빠른순", example = "yyyy-mm-dd")
    private LocalDate startDate; //가장 빠른 시작일

    @Builder
    private GetFriendStoryResponse(Long consumerId, String name, String profileImageUrl, LocalDate startDate) {
        this.consumerId = consumerId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.startDate = startDate;
    }

    public static GetFriendStoryResponse from(Funding funding, Consumer consumer) {
        return builder()
                .consumerId(consumer.getId())
                .name(consumer.getName())
                .profileImageUrl(consumer.getProfileImageUrl())
                .startDate(funding.getStartDate())
                .build();
    }

    @Override
    public int compareTo(GetFriendStoryResponse o) {
        // startDate를 기준으로 오름차순 정렬
        return this.startDate.compareTo(o.startDate);
    }
}
