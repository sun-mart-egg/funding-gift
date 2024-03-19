package com.d201.fundingift._common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST.value(), "지원되지 않는 Http Method 입니다."),
    URL_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "잘못된 URL 입니다."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "등록된 사용자가 없습니다."),
    ;

    private int code;
    private String msg;

    ErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
