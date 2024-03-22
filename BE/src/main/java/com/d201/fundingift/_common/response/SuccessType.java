package com.d201.fundingift._common.response;

import lombok.Getter;

@Getter
public enum SuccessType {

    LOGIN_SUCCESS("로그인에 성공하였습니다."),
    GET_CONSUMER_INFO_SUCCESS("사용자 정보 조회에 성공하였습니다."),
    GET_PRODUCT_CATEGORIES_SUCCESS("상품 카테고리 리스트 조회에 성공하였습니다."),
    ;

    private final String msg;

    SuccessType(String msg) {
        this.msg = msg;
    }
}
