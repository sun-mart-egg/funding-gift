package com.d201.fundingift._common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Getter
@Schema(name = "SuccessResponse", description = "성공 응답")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse <T>{

    @Schema(description = "성공 코드", example = "200")
    private int code;

    @Schema(description = "성공 메시지", example = "성공 메시지")
    private String msg;

    @Schema(description = "성공 데이터")
    private T data;

    @Builder
    private SuccessResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> SuccessResponse<T> from(T data, SuccessType successType) {
        return SuccessResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .msg(successType.getMsg())
                .data(data)
                .build();
    }

    public static <Void> SuccessResponse<Void> from(SuccessType successType) {
        return SuccessResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .msg(successType.getMsg())
                .build();
    }

}
