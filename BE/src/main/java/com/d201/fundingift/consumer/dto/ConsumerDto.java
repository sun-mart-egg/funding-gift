package com.d201.fundingift.consumer.dto;

import com.d201.fundingift.consumer.entity.Consumer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ConsumerDto {

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
    private ConsumerDto(Long id, String socialId, String email, String name, String profileImageUrl,
                        String phoneNumber, String birthyear, String birthday, String gender) {
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

    public static ConsumerDto from(Consumer consumer) {
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
