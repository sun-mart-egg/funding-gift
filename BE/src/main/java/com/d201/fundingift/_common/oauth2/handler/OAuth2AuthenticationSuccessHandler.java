package com.d201.fundingift._common.oauth2.handler;

import com.d201.fundingift._common.jwt.JwtUtil;
import com.d201.fundingift._common.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.d201.fundingift._common.oauth2.service.OAuth2UserPrincipal;
import com.d201.fundingift._common.oauth2.user.OAuth2Provider;
import com.d201.fundingift._common.oauth2.user.OAuth2UserUnlinkManager;
import com.d201.fundingift._common.oauth2.util.CookieUtils;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.service.ConsumerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.d201.fundingift._common.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.d201.fundingift._common.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final JwtUtil jwtUtil;
    private final ConsumerService consumerService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String targetUrl;

        targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been cimmitted. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if(principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        // 로그인 버튼 눌렀을 시
        if ("login".equalsIgnoreCase(mode)) {
            // TODO: 리프레시 토큰 발급
            // TODO: 리프레시 토큰 DB 저장
            log.info("email={}, name={}, nickname={}, profileUrl={}, accessToken={}",
                    principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getProfileImageUrl(),
                    principal.getUserInfo().getAccessToken()
            );

            String socialId = principal.getUserInfo().getId();
            Optional<Consumer> findMember = consumerService.findBySocialId(socialId);

            // 가입 안 된 상태일 경우 -> 회원등록
            if(findMember.isEmpty()){
                Long consumerId = consumerService.saveOAuth2User(principal);

                // Access, Refresh 토큰 생성.
                String accessToken = jwtUtil.createToken(consumerId.toString());
                String refreshToken = jwtUtil.createRefreshToken(consumerId.toString());

                // Redis 에 Access, Refresh 토큰 저장.
                jwtUtil.saveTokens(consumerId,accessToken,refreshToken);

                // Redis 에 kakaoAccess 토큰 저장.
                jwtUtil.saveKakaoAccessToken(consumerId, principal.getUserInfo().getAccessToken());

                // 회원가입 페이지로 리다이렉트(예정)
                return UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("access-token", accessToken)
                        .queryParam("consumer-id",consumerId)
                        .queryParam("next-page","sign-in")
                        .build().toUriString();
            } else {
                // 가입 된 상태일 경우 -> 로그인
                Long consumerId = findMember.get().getId();
                String accessToken = jwtUtil.createToken(consumerId.toString());

                // Redis 에 카카오 액세스 토큰 저장.
                jwtUtil.saveKakaoAccessToken(consumerId, principal.getUserInfo().getAccessToken());

                // 메인 페이지로 리다이렉트
                return UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("access-token", accessToken)
                        .queryParam("consumer-id",findMember.get().getId())
                        .queryParam("next-page","main")
                        .build().toUriString();
            }


        } else if ("unlink".equalsIgnoreCase(mode)) {
            // 회원 탈퇴
            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();
            String consumerId = principal.getName();

            consumerService.deleteConsumer(Long.valueOf(consumerId));
            oAuth2UserUnlinkManager.unlink(provider, accessToken);
            jwtUtil.deleteAccessToken(consumerId);
            jwtUtil.deleteRefreshToken(consumerId);
            jwtUtil.deleteKakaoAccessToken(consumerId);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();

    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if(principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }

        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {

        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
/**
 * OAuth2 인증 성공시 호출되는 핸들러
 * 프론트앤트에서 백엔드 로그인 요청시 mode 쿼리 파라미터에 담긴 값에 따라 분기하여 처리
 * mode=login -> 사용자 정보 DB 저장, 서비스 액세스 토큰, 리프레시 토큰 생성, 리프레시 토큰 DB 저장
 * mode=unlink -> 각 OAuth2 서비스에 맞는 연결 끊기 API 호출, 사용자 정보/ 리프레시 토큰 DB 삭제
 */
