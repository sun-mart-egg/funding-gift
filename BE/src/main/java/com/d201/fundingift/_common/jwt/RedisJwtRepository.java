package com.d201.fundingift._common.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisJwtRepository implements JwtRepository {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void saveAccessToken(Long consumerId, String accessToken) {
        redisTemplate.opsForValue().set("accessToken:" + consumerId, accessToken);
    }

    @Override
    public void saveRefreshToken(Long consumerId, String refreshToken) {
        redisTemplate.opsForValue().set("refreshToken:" + consumerId, refreshToken);
    }

    @Override
    public void saveKakaoAccessToken(Long consumerId, String kakaoAccessToken) {
        redisTemplate.opsForValue().set("kakaoAccessToken:" + consumerId, kakaoAccessToken);
    }

    @Override
    public String getAccessToken(Long consumerId) {
        return (String) redisTemplate.opsForValue().get("accessToken:" + consumerId);
    }

    @Override
    public String getRefreshToken(Long consumerId) {
        return (String) redisTemplate.opsForValue().get("refreshToken:" + consumerId);
    }

    @Override
    public String getKakaoAccessToken(Long consumerId) {
        return (String) redisTemplate.opsForValue().get("kakaoAccessToken:" + consumerId);
    }

    @Override
    public void deleteAccessToken(Long consumerId) {
        redisTemplate.delete("accessToken:" + consumerId);
    }

    @Override
    public void deleteRefreshToken(Long consumerId) {
        redisTemplate.delete("refreshToken:" + consumerId);
    }

    @Override
    public void deleteKakaoAccessToken(Long consumerId) {
        redisTemplate.delete("kakaoAccessToken:" + consumerId);
    }
}
