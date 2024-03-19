package com.d201.fundingift._common.exception;

import com.d201.fundingift._common.response.ApiResponseDto;
import com.d201.fundingift._common.response.ErrorResponseDto;
import com.d201.fundingift._common.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.d201.fundingift._common.response.ErrorType.METHOD_NOT_SUPPORTED;
import static com.d201.fundingift._common.response.ErrorType.URL_NOT_FOUND;

@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> validException(MethodArgumentNotValidException e) {
        ErrorResponseDto responseDto = ErrorResponseDto.from(e.getBindingResult());
        log.error("validException throw Exception : {}", e.getBindingResult());

        return ResponseEntity.badRequest().body(ResponseUtils.error(responseDto));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponseDto<Void>> httpMethodException(HttpRequestMethodNotSupportedException e) {
        ErrorResponseDto responseDto = ErrorResponseDto.from(METHOD_NOT_SUPPORTED);
        log.error("httpMethodException throw Exception : {}", METHOD_NOT_SUPPORTED);

        return ResponseEntity.badRequest().body(ResponseUtils.error(responseDto));
    }

    @ExceptionHandler(value = {
            NoHandlerFoundException.class,
            TypeMismatchException.class})
    public ResponseEntity<ApiResponseDto<Void>> urlException(Exception e) {
        ErrorResponseDto responseDto = ErrorResponseDto.from(URL_NOT_FOUND);
        log.error("urlException throw Exception : {}", URL_NOT_FOUND);

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
