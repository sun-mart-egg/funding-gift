 package com.d201.fundingift.consumer.dto;

import com.d201.fundingift.consumer.entity.Consumer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SecurityMemberDto {

    private Long id;
    private String socialId;
    private String email;

    @Builder
    private SecurityMemberDto(Long id, String socialId, String email) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
    }

    public static SecurityMemberDto from(Consumer consumer) {
        return builder()
                .id(consumer.getId())
                .socialId(consumer.getSocialId())
                .email(consumer.getEmail())
                .build();
    }

}

