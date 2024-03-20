package com.d201.fundingift._common.response;

import org.springframework.validation.BindingResult;

public class ResponseUtils {

    public static <T> SuccessResponse<T> ok(T data, SuccessType successType) {
        return SuccessResponse.from(data, successType);
    }

    public static SuccessResponse<Void> ok(SuccessType successType) {
        return SuccessResponse.from(successType);
    }

    public static ErrorResponse error(ErrorType errorType) {
        return ErrorResponse.from(errorType);
    }

    public static ErrorResponse error(BindingResult bindingResult) {
        return ErrorResponse.from(bindingResult);
    }

}
