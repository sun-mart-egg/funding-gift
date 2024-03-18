package com.d201.fundingift._common.response;

public class ResponseUtils {
    public static <T> ApiResponseDto<T> ok(T data, SuccessType successType) {
        return ApiResponseDto.<T>builder()
                .data(data)
                .msg(successType.getMsg())
                .build();
    }

    public static ApiResponseDto<Void> ok(SuccessType successType) {
        return ApiResponseDto.<Void>builder()
                .msg(successType.getMsg())
                .build();
    }

    public static ApiResponseDto<Void> error(ErrorResponseDto error) {
        return ApiResponseDto.<Void>builder()
                .error(error)
                .build();
    }
}
