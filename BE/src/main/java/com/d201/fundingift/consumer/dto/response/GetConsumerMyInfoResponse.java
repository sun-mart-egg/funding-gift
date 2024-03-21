package com.d201.fundingift.consumer.dto.response;

import com.d201.fundingift.consumer.entity.Consumer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Schema(name = "GetConsumerMyResponse", description = "소비자 내 정보 조회 응답")
@Getter
@ToString
public class GetConsumerMyInfoResponse {

    @Schema(description = "소비자 ID", example = "1", required = true)
    private final Long id;

    @Schema(description = "소셜 ID", example = "3386578453")
    private final String socialId;

    @Schema(description = "이메일 주소", example = "user@example.com")
    private final String email;

    @Schema(description = "이름", example = "홍길동")
    private final String name;

    @Schema(description = "프로필 이미지 URL", example = "http://example.com/profile.jpg")
    private final String profileImageUrl;

    @Schema(description = "전화번호", example = "+82 010-1234-5678")
    private final String phoneNumber;

    @Schema(description = "출생년도", example = "1996")
    private final String birthyear;

    @Schema(description = "생일", example = "0221")
    private final String birthday;

    @Schema(description = "성별", example = "male")
    private final String gender;


    @Builder
    private GetConsumerMyInfoResponse(Long id, String socialId, String email, String name, String profileImageUrl, String phoneNumber, String birthyear, String birthday, String gender) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.phoneNumber = phoneNumber;
        this.birthyear = birthyear;
        this.birthday = birthday;
        this.gender = gender;
    }

    // 직접 값을 전달하여 ConsumerInfoResponseDto 인스턴스를 생성
    public static GetConsumerMyInfoResponse of(Long id, String socialId, String email, String name, String profileImageUrl, String phoneNumber, String birthyear, String birthday, String gender) {
        return builder()
                .id(id)
                .socialId(socialId)
                .email(email)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .phoneNumber(phoneNumber)
                .birthyear(birthyear)
                .birthday(birthday)
                .gender(gender)
                .build();
    }

    // Consumer 엔티티로부터 ConsumerInfoResponseDto 인스턴스를 생성
    public static GetConsumerMyInfoResponse from(Consumer consumer) {
        return builder()
                .id(consumer.getId())
                .socialId(consumer.getSocialId())
                .email(consumer.getEmail())
                .name(consumer.getName())
                .profileImageUrl(consumer.getProfileImageUrl())
                .phoneNumber(consumer.getPhoneNumber())
                .birthyear(consumer.getBirthyear())
                .birthday(consumer.getBirthday())
                .gender(consumer.getGender())
                .build();
    }
}
