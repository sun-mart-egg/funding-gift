package com.d201.fundingift._common.oauth2.user;

import java.util.HashMap;
import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String profileImageUrl;

    public KakaoOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;

        // attributes 맵의 kakao_account 키의 값에 실제 attributes 맵이 할당되어 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        this.attributes = new HashMap<>();

        this.id = ((Long) attributes.get("id")).toString();
        this.email = (String) kakaoAccount.get("email");

        //this.name = (String) kakaoAccount.get("name");
        // 임시로 닉네임을 가져온다. (이름 권한 동의 X)
        this.name =(String) kakaoProfile.get("nickname");
        
        this.firstName = null;
        this.lastName = null;
        this.nickName = (String) kakaoProfile.get("nickname");

        this.profileImageUrl = (String) kakaoProfile.get("profile_image_url");

        this.attributes.put("id", id);
        this.attributes.put("email", this.email);
        this.attributes.put("nickname", nickName);
        this.attributes.put("profile_image_url", profileImageUrl);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}