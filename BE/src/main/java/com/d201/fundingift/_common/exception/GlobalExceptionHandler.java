package com.d201.fundingift._common.exception;

import com.d201.fundingift._common.response.ApiResponseDto;
import com.d201.fundingift._common.response.ErrorResponseDto;
import com.d201.fundingift._common.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class )
    public ResponseEntity<ApiResponseDto<Void>> methodValidException(MethodArgumentNotValidException e) {
        ErrorResponseDto responseDto = ErrorResponseDto.from(e.getBindingResult());
        log.error("methodValidException throw Exception : {}", e.getBindingResult());

        return ResponseEntity.badRequest().body(ResponseUtils.error(responseDto));
    }

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<ApiResponseDto<Void>> handleCustomException(CustomException e) {
        ErrorResponseDto responseDto = ErrorResponseDto.from(e.getErrorType());
        log.error("handleDataException throw Exception : {}", e.getErrorType());

        return ResponseEntity
                .status(e.getErrorType().getCode())
                .body(ResponseUtils.error(responseDto));
    }

}
