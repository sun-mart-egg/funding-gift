package com.d201.fundingift._common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 Http Method 입니다."),
    URL_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 URL 입니다."),

    USER_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "인증되지 않은 사용자입니다."),
    CONSUMER_NOT_FOUND(HttpStatus.BAD_REQUEST,"소비자를 찾을 수 없습니다."),

    ANNIVERSARY_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "기념일 카테고리를 찾을 수 없습니다."),
    FUNDING_DURATION_NOT_VALID(HttpStatus.BAD_REQUEST, "펀딩 기간이 7일을 넘습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "제품을 찾을 수 없습니다."),
    PRODUCT_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "제품 옵션을 찾을 수 없습니다."),
    ;

    private HttpStatus httpStatus;
    private String msg;

    ErrorType(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
