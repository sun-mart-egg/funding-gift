package com.d201.fundingift._common.oauth2.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
/**
 * OAuth2 인증 관련 실패를 위한 사용자 정의 예외 클래스
 */