package com.d201.fundingift._common.response;

import com.amazonaws.services.ec2.model.transform.ResetEbsDefaultKmsKeyIdResultStaxUnmarshaller;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    /**
     * GLOBAL ERROR
     */
    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 Http Method 입니다."),
    URL_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 URL 입니다."),
    PATH_VARIABLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "Path Variable 이 없습니다."),
    REQUEST_PARAM_NOT_FOUND(HttpStatus.BAD_REQUEST, "Request Param 이 없습니다."),

    /**
     * CUSTOM ERROR
     */
    SORT_NOT_FOUND(HttpStatus.BAD_REQUEST, "정렬 조건을 찾을 수 없습니다."),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, "파일의 포맷이 올바르지 않습니다."),
    IMAGE_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 파일 업로드에 실패하였습니다."),

    // 토큰
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "Http Header에 토큰이 없습니다."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    // 사용자
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록된 사용자가 없습니다."),
    CONSUMER_NOT_FOUND(HttpStatus.BAD_REQUEST,"소비자를 찾을 수 없습니다."),
    CANNOT_DELETE_CONSUMER_WITH_IN_PROGRESS_FUNDING(HttpStatus.BAD_REQUEST,"현재 참여 중이거나 진행 중인 펀딩이 있어 탈퇴할 수 없습니다."),

    //친구
    FRIEND_NOT_FOUND(HttpStatus.BAD_REQUEST, "내 친구가 아닙니다."),
    FRIEND_NOT_IS_FAVORITE(HttpStatus.BAD_REQUEST, "친한 친구가 아닙니다."),
    KAKAO_FRIEND_NOT_FOUND(HttpStatus.BAD_REQUEST, "카카오 친구목록 조회에 실패하였습니다."),
    FRIEND_RELATIONSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "친구 관계를 찾을 수 없습니다"),
    FRIEND_RELATIONSHIP_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "친구 관계 삭제 중 오류 발생"),

    // 펀딩
    ANNIVERSARY_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 기념일 카테고리를 찾을 수 없습니다."),
    FUNDING_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 펀딩을 찾을 수 없습니다."),
    FUNDING_NOT_MINE(HttpStatus.BAD_REQUEST, "내 펀딩이 아닙니다."),
    FUNDING_STATUS_NOT_DELETED(HttpStatus.BAD_REQUEST, "펀딩을 삭제할 수 있는 상태가 아닙니다."),
    FUNDING_DURATION_NOT_VALID(HttpStatus.BAD_REQUEST, "펀딩 기간이 7일을 넘습니다."),
    FUNDING_NOT_STARTED(HttpStatus.BAD_REQUEST, "시작하지 않은 펀딩입니다."),
    FUNDING_FINISHED(HttpStatus.BAD_REQUEST, "종료된 펀딩 입니다."),
    FUNDING_START_DATE_IS_PAST(HttpStatus.BAD_REQUEST, "펀딩 시작일이 과거입니다."),
    FUNDING_END_DATE_IS_PAST(HttpStatus.BAD_REQUEST, "펀딩 종료일이 기념일보다 과거입니다."),
    FUNDING_ANNIVERSARY_DATE_IS_PAST(HttpStatus.BAD_REQUEST, "기념일이 시작일보다 과거입니다."),

    //펀딩 참여
    FUNDING_NOT_VERIFY_MIN_PRICE(HttpStatus.BAD_REQUEST,"펀딩 참여 금액이 최소 금액을 만족하지 않습니다."),
    FUNDING_OVER_TARGET_PRICE(HttpStatus.BAD_REQUEST, "펀딩 목표 금액을 초과했습니다."),
    ATTENDANCE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 펀딩 참여 정보를 찾을 수 없습니다."),

    // 상품
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품을 찾을 수 없습니다."),
    PRODUCT_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품 옵션을 찾을 수 없습니다."),
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품 카테고리를 찾을 수 없습니다."),
    PRODUCT_OPTION_MISMATCH(HttpStatus.BAD_REQUEST, "상품 옵션이 상품과 맞지 않습니다."),

    // 리뷰
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 리뷰를 찾을 수 없습니다."),

    // 위시리스트
    WISHLIST_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 위시리스트에 등록된 상품입니다."),
    WISHLIST_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 위시리스트를 찾을 수 없습니다."),

    //주소
    ADDRESS_NOT_FOUND(HttpStatus.BAD_REQUEST, "주소를 찾을 수 없습니다."),

    //알람
    ALARM_NOT_FOUND(HttpStatus.BAD_REQUEST,"알람을 찾을 수 없습니다."),
    INVALID_ALARM_ID(HttpStatus.BAD_REQUEST, "유효하지 않는 알람 형식입니다"),
    ALARM_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알람 생성 중 오류 발생"),
    ALARM_RETRIEVAL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알람 조회 중 오류 발생"),
    ALARM_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알람 읽음 상태 업데이트 중 오류 발생"),
    ALARM_DELETION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알람 삭제 중 오류 발생"),
    ALARM_DELETION_BY_USER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 ID로 알람 삭제 중 오류 발생"),
    ;

    private HttpStatus httpStatus;
    private String msg;

    ErrorType(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
