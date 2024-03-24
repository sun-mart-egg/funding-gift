package com.d201.fundingift._common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // GLOBAL ERROR
    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 Http Method 입니다."),
    URL_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 URL 입니다."),
    PATH_VARIABLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "Path Variable 이 없습니다."),
    REQUEST_PARAM_NOT_FOUND(HttpStatus.BAD_REQUEST, "Request Param 이 없습니다."),

    // CUSTOM ERROR
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록된 사용자가 없습니다."),
    USER_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "인증되지 않은 사용자입니다."),
    CONSUMER_NOT_FOUND(HttpStatus.BAD_REQUEST,"사용자를 찾을 수 없습니다."),
    SORT_NOT_FOUND(HttpStatus.BAD_REQUEST, "정렬 조건을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 카테고리를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품을 찾을 수 없습니다."),
    ;

    private HttpStatus httpStatus;
    private String msg;

    ErrorType(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
