package com.d201.fundingift._common.config;

import com.d201.fundingift._common.jwt.JwtAuthorizationFilter;
import com.d201.fundingift._common.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.d201.fundingift._common.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.d201.fundingift._common.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.d201.fundingift._common.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정 (기본 설정 사용)
//                .cors(Customizer.withDefaults())
                // CSRF 보호 기능 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP 기본 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // For H2 DB
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // For H2 DB
                // 세션 정책 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 X, (있더라도) 사용 X
                )
                // 요청에 대한 권한 설정
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(antMatcher("/api/consumers/**")).authenticated()
//                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
//                        .requestMatchers(antMatcher("/swagger-ui/")).permitAll()
                        .anyRequest().permitAll()
                )
                // OAuth2 로그인 설정
                .oauth2Login(configure ->
                        configure.authorizationEndpoint(config -> config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                                .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
                                .failureHandler(oAuth2AuthenticationFailureHandler) // 로그인 실패 핸들러
                                .successHandler(oAuth2AuthenticationSuccessHandler) // 로그인 성공 핸들러
                );

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

/**
 * 스프링 시큐리티, OAuth2
 * CSRF설정 Disable
 * OAuth2 핸들러 및 서비스 빈으로 등록
 */
