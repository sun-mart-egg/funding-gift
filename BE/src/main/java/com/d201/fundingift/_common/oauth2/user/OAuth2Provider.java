package com.d201.fundingift._common.oauth2.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {

    //GOOGLE("google"),
    KAKAO("kakao");

    private final String registrationId;
}
