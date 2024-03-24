package com.d201.fundingift._common.exception;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.d201.fundingift._common.response.ErrorType.*;

@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    // validation error
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException e) {
        log.error("validException throw Exception : {}", e.getBindingResult());

        return ResponseEntity.badRequest().body(ResponseUtils.error(e.getBindingResult()));
    }

    // http method error
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpMethodException(HttpRequestMethodNotSupportedException e) {
        log.error("httpMethodException throw Exception : {}", (Object) e.getStackTrace());

        return ResponseEntity.badRequest().body(ResponseUtils.error(METHOD_NOT_SUPPORTED));
    }

    // url error
    @ExceptionHandler(value = {
            NoHandlerFoundException.class,
            TypeMismatchException.class})
    public ResponseEntity<ErrorResponse> urlException(Exception e) {
        log.error("urlException throw Exception : {}", (Object) e.getStackTrace());

        return ResponseEntity.badRequest().body(ResponseUtils.error(URL_NOT_FOUND));
    }

    // path variable error
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> pathVariableException(Exception e) {
        log.error("pathVariableException throw Exception : {}", (Object) e.getStackTrace());

        return ResponseEntity.badRequest().body(ResponseUtils.error(PATH_VARIABLE_NOT_FOUND));
    }

    // request param error
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> requestParamException(Exception e) {
        log.error("requestParamException throw Exception : {}", (Object) e.getStackTrace());

        return ResponseEntity.badRequest().body(ResponseUtils.error(REQUEST_PARAM_NOT_FOUND));
    }

    // custom error
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleDataException throw Exception : {}", e.getErrorType());

        return ResponseEntity
                .status(e.getErrorType().getHttpStatus().value())
                .body(ResponseUtils.error(e.getErrorType()));
    }

}
