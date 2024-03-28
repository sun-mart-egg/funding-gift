package com.d201.fundingift._common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Schema(name = "ErrorResponse", description = "에러 응답")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "400")
    private int code;

    @Schema(description = "에러 상태", example = "BAD_REQUEST")
    private String status;

    @Schema(description = "에러 메시지", example = "에러 메시지")
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
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                message += error.getDefaultMessage() + " ";
            }
        }

        return builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .msg(message)
                .build();
    }

}
