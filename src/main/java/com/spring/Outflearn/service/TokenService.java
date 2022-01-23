package com.spring.Outflearn.service;


import com.spring.Outflearn.config.jwt.TokenDto;
import com.spring.Outflearn.config.jwt.TokenRequestDto;

public interface TokenService {
//    TokenDto login(UserLoginRequestDto userLoginRequestDto);
    TokenDto refresh(TokenRequestDto tokenRequestDto);
    TokenRequestDto setTokenRequestDto(String accessToken, String refreshToken);
}
