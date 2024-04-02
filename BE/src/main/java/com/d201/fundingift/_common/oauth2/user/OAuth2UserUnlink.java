package com.d201.fundingift._common.oauth2.user;

public interface OAuth2UserUnlink {

    void unlink(String accessToken);
}
/**
 * OAuth2 애플리케이션과 연동 해제 하는 방법이 OAuth2 제공자별로 다름
 * -> 서비스 별로 다른 연동 해제 방법을 통합 하기 위한 인터페이스를 정의
 */