package com.d201.fundingift._common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    //private static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 30; // 30min
    private static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 3000; // 3000분 개발용
    private static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 60 * 24 * 7; // 7일
    private final RedisTemplate redisTemplate;

    
    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64URL.decode(secret);
        this.key = Keys.hmacShaKeyFor(key);
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT is not valid");
        } catch (SignatureException exception) {
            log.error("JWT signature validation fails");
        } catch (ExpiredJwtException exception) {
            log.error("JWT is expired");
        } catch (IllegalArgumentException exception) {
            log.error("JWT is null or empty or only whitespace");
        } catch (Exception exception) {
            log.error("JWT validation fails", exception);
        }

        return false;
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("Refresh Token validation error: {}", e.getMessage());
        }
        return false;
    }

    public String createToken(String consumerId) {

        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);

        return Jwts.builder()
                .setSubject(consumerId)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String consumerId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);

        return Jwts.builder()
                .setSubject(consumerId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        UserDetails user = new User(claims.getSubject(), "", Collections.emptyList());

        return new UsernamePasswordAuthenticationToken(user, "", Collections.emptyList());
    }

    // 토큰으로 부터 consumerID 추출.
    public String extractUserId(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public void saveTokens(Long userId, String accessToken, String refreshToken) {
        redisTemplate.opsForValue().set("accessToken:" + userId, accessToken);
        redisTemplate.opsForValue().set("refreshToken:" + userId, refreshToken);
    }

    public void saveKakaoAccessToken(Long consumerId, String accessToken) {
        redisTemplate.opsForValue().set("kakaoAccessToken:" + consumerId, accessToken);
    }

    public String getAccessToken(Long consumerId) {
        return (String) redisTemplate.opsForValue().get("accessToken:" + consumerId);
    }

    public String getRefreshToken(Long consumerId) {
        return (String) redisTemplate.opsForValue().get("refreshToken:" + consumerId);
    }

    public String getKakaoAccessToken(String consumerId) {
        return (String) redisTemplate.opsForValue().get("kakaoAccessToken:" + consumerId);
    }

    public void deleteAccessToken(String consumerId) {
        redisTemplate.delete("accessToken:" + consumerId);
    }

    public void deleteRefreshToken(String consumerId) {
        redisTemplate.delete("refreshToken:" + consumerId);
    }

    public void deleteKakaoAccessToken(String consumerId) {
        redisTemplate.delete("kakaoAccessToken:" + consumerId);
    }
}
