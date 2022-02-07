package com.spring.usinsa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface OAuthService {

    TokenDto oauthLogin(String email, String social, String socialId);
    void guideSocial(String email);

    KakaoProfile getKakaoProfile(String accessToken);
//    KakaoTokenDto getKakaoTokenInfo(String code);

    NaverProfile getNaverProfile(String accessToken);
    ResponseEntity<Object> getGoogleLoginForm();
    GoogleProfile getGoogleProfile(String idToken) throws JsonProcessingException;
    Object checkProfile(String social, String socialId, String email, Object object);
}
