package com.d201.fundingift._common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {

    private int code;
    private String status;
    private String msg;

    @Builder
    private ErrorResponse(int code, String status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public static ErrorResponse from(ErrorType errorType) {
        return builder()
                .code(errorType.getHttpStatus().value())
                .status(errorType.getHttpStatus().name())
                .msg(errorType.getMsg())
                .build();
    }

    public  static ErrorResponse from(BindingResult bindingResult) {
        String message = "";

        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        return builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .msg(message)
                .build();
    }

}
