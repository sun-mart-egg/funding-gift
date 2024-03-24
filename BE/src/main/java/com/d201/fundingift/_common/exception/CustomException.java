package com.d201.fundingift._common.exception;

import com.d201.fundingift._common.response.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorType errorType;

}
