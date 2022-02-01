package com.spring.outflearn.service;


import com.spring.outflearn.config.jwt.TokenDto;
import com.spring.outflearn.config.jwt.TokenRequestDto;

public interface TokenService {
//    TokenDto login(UserLoginRequestDto userLoginRequestDto);
    TokenDto refresh(TokenRequestDto tokenRequestDto);
    TokenRequestDto setTokenRequestDto(String accessToken, String refreshToken);
}
