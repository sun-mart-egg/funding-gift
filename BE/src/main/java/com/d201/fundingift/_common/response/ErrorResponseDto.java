package com.d201.fundingift._common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponseDto {

    private int status;
    private String msg;

    @Builder
    private ErrorResponseDto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static ErrorResponseDto from(ErrorType errorType) {
        return ErrorResponseDto.builder()
                .status(errorType.getCode())
                .msg(errorType.getMsg())
                .build();
    }

    public static ErrorResponseDto from(String msg){
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .msg(msg)
                .build();
    }

    public static ErrorResponseDto of(String msg, int status){
        return ErrorResponseDto.builder()
                .status(status)
                .msg(msg)
                .build();
    }

    public static ErrorResponseDto from(BindingResult bindingResult) {
        String message = "";

        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .msg(message)
                .build();
    }

}
