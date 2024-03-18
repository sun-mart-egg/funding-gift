package com.d201.fundingift._common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto <T>{

    private T data;
    private String msg;
    private ErrorResponseDto error;

    @Builder
    private ApiResponseDto(T data, String msg, ErrorResponseDto error) {
        this.data = data;
        this.msg = msg;
        this.error = error;
    }

}
