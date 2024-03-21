package com.d201.fundingift._common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 Http Method 입니다."),
    URL_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 URL 입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록된 사용자가 없습니다."),
    USER_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "인증되지 않은 사용자입니다."),
    CONSUMER_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다.")
    ;

    private HttpStatus httpStatus;
    private String msg;

    ErrorType(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
