package com.d201.fundingift._common.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {
    // CustomException에 포함시켜야 하는지..?
    private final HttpStatus status;

    public HttpException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

