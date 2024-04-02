package com.d201.fundingift._common.response;

import lombok.Getter;

@Getter
public enum SuccessType {
    // 사용자
    LOGIN_SUCCESS("로그인에 성공하였습니다."),
    LOGOUT_SUCCESS("로그아웃에 성공하였습니다."),
    GET_CONSUMER_INFO_SUCCESS("사용자 정보 조회에 성공하였습니다."),
    PUT_CONSUMER_ADDITION_INFO_SUCCESS("추가 정보 입력에 성공하였습니다."),
    CHECK_CONSUMER_IN_PROGRESS_FUNDING("진행중인 펀딩 확인에 성공하였습니다."),

    // 친구
    GET_KAKAO_FRIEND_INFO_SUCCESS("카카오 친구 목록 조회에 성공하였습니다."),
    GET_FRIEND_INFO_SUCCESS("친구 목록 조회에 성공하였습니다."),
    GET_FRIENDS_STORY_SUCCESS("친구 펀딩 스토리 리스트 조회에 성공하였습니다."),
    PUT_FAVORITE_TOGGLE_SUCCESS("친한 친구 토글에 설공하였습니다."),

    // 펀딩
    CREATE_FUNDING_SUCCESS("펀딩 등록에 성공하였습니다."),
    GET_MY_FUNDINGS_SUCCESS("내 펀딩 목록 조회에 성공하였습니다."),
    GET_FRIEND_FUNDINGS_SUCCESS("친구 펀딩 목록 조회에 성공하였습니다."),
    GET_FUNDINGS_STORY_SUCCESS("펀딩 목록 스토리 조회에 성공하였습니다."),
    GET_FUNDING_CALENDARS_SUCCESS("펀딩 캘린더 목록 조회에 성공하였습니다."),
    GET_FUNDINGS_FEED_SUCCESS("펀딩 목록 피드 조회에 성공하였습니다."),
    GET_FUNDING_DETAIL_SUCCESS("펀딩 상세 조회에 성공하였습니다."),
    GET_ATTENDANCE_DETAIL_SUCCESS("펀딩 참여 상세 리스트 조회에 성공하였습니다."),
    POST_ATTENDANCE_SUCCESS("펀딩 참여에 성공하였습니다."),

    // 상품
    GET_PRODUCT_CATEGORIES_SUCCESS("상품 카테고리 목록 조회에 성공하였습니다."),
    GET_PRODUCTS_SUCCESS("상품 목록 조회에 성공하였습니다."),
    GET_PRODUCTS_RANK_SUCCESS("순위 별 상품 목록 조회에 성공하였습니다."),
    GET_PRODUCT_DETAIL_SUCCESS("상품 상세 조회에 성공하였습니다."),

    // 리뷰
    CREATE_REVIEW_SUCCESS("리뷰 등록에 성공하였습니다."),
    GET_REVIEWS_BY_PRODUCT_SUCCESS("상품 별 리뷰 목록 조회에 성공하였습니다."),
    UPDATE_REVIEW_SUCCESS("리뷰 수정에 성공하였습니다."),
    DELETE_REVIEW_SUCCESS("리뷰 삭제에 성공하였습니다."),

    // 위시리스트
    CREATE_WISHLIST_SUCCESS("위시리스트 등록에 성공하였습니다."),
    DELETE_WISHLIST_SUCCESS("위시리스트 삭제에 성공하였습니다."),
    GET_WISHLISTS_SUCCESS("위시리스트 목록 조회에 성공하였습니다."),

    // 주소
    CREATE_ADDRESS_SUCCESS("주소가 성공적으로 추가되었습니다."),
    GET_ADDRESSES_SUCCESS("주소가 성공적으로 조회되었습니다."),
    UPDATE_ADDRESS_SUCCESS("주소가 성공적으로 수정되었습니다."),
    DELETE_ADDRESS_SUCCESS("주소가 성공적으로 삭제되었습니다."),

    // 알림
    CREATE_ALARM_SUCCESS("알람이 성공적으로 추가되었습니다."),
    GET_ALARM_SUCCESS("알람이 성공적으로 조회되었습니다."),
    UPDATE_ALARM_SUCCESS("알람이 성공적으로 수정되었습니다."),
    DELETE_ALARM_SUCCESS("알람이 성공적으로 삭제되었습니다."),
    ;

    private final String msg;

    SuccessType(String msg) {
        this.msg = msg;
    }
}
