package com.d201.fundingift._common.jwt;

public interface JwtRepository {
    void saveAccessToken(Long consumerId, String accessToken);
    void saveRefreshToken(Long consumerId, String refreshToken);
    void saveKakaoAccessToken(Long consumerId, String kakaoAccessToken);

    String getAccessToken(Long consumerId);
    String getRefreshToken(Long consumerId);
    String getKakaoAccessToken(Long consumerId);

    void deleteAccessToken(Long consumerId);
    void deleteRefreshToken(Long consumerId);
    void deleteKakaoAccessToken(Long consumerId);
}