package com.d201.fundingift._common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate; // RedisTemplate 추가

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        boolean isTokenRefreshed = false;

        logger.info("JwtAuthorizationFilter: Filtering request");

        if (StringUtils.hasText(token)) {
            logger.info("JwtAuthorizationFilter: Token found: " + token);
            if (jwtUtil.validateAccessToken(token)) {
                logger.info("JwtAuthorizationFilter: Valid access token");
                Authentication authentication = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (jwtUtil.isTokenExpired(token)) {
                logger.info("JwtAuthorizationFilter: Token is expired");
                String userId = jwtUtil.extractUserIdFromExpiredToken(token);
                logger.info("JwtAuthorizationFilter: Extracted userId: " + userId);
                String refreshToken = redisTemplate.opsForValue().get("refreshToken:" + userId);
                if (refreshToken != null && jwtUtil.validateRefreshToken(refreshToken)) {
                    logger.info("JwtAuthorizationFilter: Valid refresh token");
                    String newAccessToken = jwtUtil.createAccessToken(userId);
                    SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(newAccessToken));
                    response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + newAccessToken);
                    isTokenRefreshed = true;
                    logger.info("JwtAuthorizationFilter: Access token refreshed");
                } else {
                    logger.info("JwtAuthorizationFilter: Refresh token is invalid or missing");
                }
            } else {
                logger.info("JwtAuthorizationFilter: Access token is invalid");
            }
        } else {
            logger.info("JwtAuthorizationFilter: No token found in the request");
        }

        filterChain.doFilter(request, response);
        logger.info("JwtAuthorizationFilter: Request processed");

        // 토큰이 새롭게 발급되었다면, 해당 정보를 클라이언트에게 전달
        if (isTokenRefreshed) {
            response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION_HEADER);
            logger.info("JwtAuthorizationFilter: New token added to response header");
        }
    }



    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
