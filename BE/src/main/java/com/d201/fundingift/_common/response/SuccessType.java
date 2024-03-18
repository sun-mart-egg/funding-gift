package com.d201.fundingift._common.response;

import lombok.Getter;

@Getter
public enum SuccessType {

    LOGIN_SUCCESS("로그인 성공"),
    ;

    private final String msg;

    SuccessType(String msg) {
        this.msg = msg;
    }
}
