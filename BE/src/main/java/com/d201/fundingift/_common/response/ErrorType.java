package com.d201.fundingift._common.response;

import lombok.Getter;

@Getter
public enum ErrorType {

    NOT_FOUND_USER(401, "등록된 사용자가 없습니다."),
    ;

    private int code;
    private String msg;

    ErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
