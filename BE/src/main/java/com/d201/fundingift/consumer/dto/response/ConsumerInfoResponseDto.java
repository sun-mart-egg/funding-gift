package com.d201.fundingift.consumer.dto.response;

import com.d201.fundingift.consumer.entity.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ConsumerInfoResponseDto {

    private final Long id;
    private final String socialId;
    private final String email;
    private final String name;
    private final String profileImageUrl;
    private final String phoneNumber;
    private final String birthyear;
    private final String birthday;
    private final String gender;

    @Builder
    public ConsumerInfoResponseDto(Long id, String socialId, String email, String name, String profileImageUrl, String phoneNumber, String birthyear, String birthday, String gender) {
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
    public static ConsumerInfoResponseDto of(Long id, String socialId, String email, String name, String profileImageUrl, String phoneNumber, String birthyear, String birthday, String gender) {
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
    public static ConsumerInfoResponseDto from(Consumer consumer) {
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
