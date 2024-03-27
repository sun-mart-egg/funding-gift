package com.d201.fundingift._common.response;

import lombok.Getter;

@Getter
public enum SuccessType {

    LOGIN_SUCCESS("로그인에 성공하였습니다."),
    GET_CONSUMER_INFO_SUCCESS("사용자 정보 조회에 성공하였습니다."),
    GET_KAKAO_FRIEND_INFO_SUCCESS("카카오 친구목록 조회에 성공하였습니다."),
    GET_FRIEND_INFO_SUCCESS("친구목록 조회에 성공하였습니다."),
    GET_PRODUCT_CATEGORIES_SUCCESS("상품 카테고리 리스트 조회에 성공하였습니다."),
    CREATE_FUNDING_SUCCESS("펀딩 등록에 성공하였습니다."),
    GET_MY_FUNDINGS_SUCCESS("내 펀딩 목록 조회에 성공하였습니다."),
    GET_FRIEND_FUNDINGS_SUCCESS("친구 펀딩 목록 조회에 성공하였습니다."),
    GET_PRODUCTS_BY_CATEGORY_SUCCESS("카테고리 별 상품 리스트 조회에 성공하였습니다."),
    GET_PRODUCTS_BY_KEYWORD_SUCCESS("검색어 별 상품 리스트 조회에 성공하였습니다."),
    GET_PRODUCT_DETAIL_SUCCESS("상품 상세 조회에 성공하였습니다."),
    GET_REVIEWS_BY_PRODUCT_SUCCESS("상품 별 리뷰 리스트 조회에 성공하였습니다."),
    ;

    private final String msg;

    SuccessType(String msg) {
        this.msg = msg;
    }
}
