package com.d201.fundingift.consumer.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Schema(name = "PutConsumerInfoRequestDto", description = "소비자 정보를 수정하는데 필요한 정보들입니다.")
public class PutConsumerInfoRequestDto {
    @Length(min = 1, max = 10, message = "name: 값이 1에서 10 사이여야 합니다.")
    @Schema(description = "이름", example = "박창준")
    private String name;

    @Email(message = "email: 유효한 이메일 주소여야 합니다.")
    @Length(max = 50, message = "email: 값이 최대 50자여야 합니다.")
    @Schema(description = "이메일", example = "qmak01@naver.com")
    private String email;

    @Pattern(regexp = "^\\d{10,11}$", message = "phoneNumber: 유효한 전화번호여야 합니다.")
    @Schema(description = "번호", example = "01026698294")
    private String phoneNumber;

    @Length(min = 4, max = 4, message = "birthyear: 값은 4자리 숫자여야 합니다.")
    @Pattern(regexp = "\\d{4}", message = "birthyear: 유효하지 않은 연도 형식입니다.")
    @Schema(description = "출생년도", example = "1996")
    private String birthyear;

    @Length(min = 4, max = 4, message = "birthday: 값은 4자리 숫자여야 합니다.")
    @Pattern(regexp = "\\d{4}", message = "birthday: 유효하지 않은 날짜 형식입니다.")
    @Schema(description = "생일", example = "0221")
    private String birthday;

    @Pattern(regexp = "^(male|female)$", message = "gender: 값은 'male' 또는 'female' 이어야 합니다.")
    @Schema(description = "성별", example = "male")
    private String gender;
}
