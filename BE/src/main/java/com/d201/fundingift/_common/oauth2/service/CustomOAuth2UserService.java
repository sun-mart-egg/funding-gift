package com.d201.fundingift._common.oauth2.service;

import com.d201.fundingift._common.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.d201.fundingift._common.oauth2.user.OAuth2UserInfo;
import com.d201.fundingift._common.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * CustomOAuth2UserService 클래스는 OAuth2 로그인 프로세스 중 사용자 정보를 로드하는 서비스를 정의합니다.
 * loadUser 메서드는 Spring Security의 OAuth2LoginAuthenticationFilter에 의해 호출되며,
 * OAuth2 제공자로부터 액세스 토큰을 받은 후 사용자 정보를 로드하는 시점에 호출됩니다.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // AuthenticationException의 인스턴스를 throw하면 OAuth2AuthenticationFailureHandler가 트리거됩니다.
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, accessToken, oAuth2User.getAttributes());

        // OAuth2UserInfo 필드 검증
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 제공자로부터 이메일을 찾을 수 없습니다.");
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
