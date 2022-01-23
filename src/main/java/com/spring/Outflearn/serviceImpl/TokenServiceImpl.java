package com.spring.Outflearn.serviceImpl;

import com.spring.Outflearn.config.jwt.JwtTokenProvider;
import com.spring.Outflearn.config.jwt.TokenDto;
import com.spring.Outflearn.config.jwt.TokenRequestDto;
import com.spring.Outflearn.exception.ApiErrorCode;
import com.spring.Outflearn.exception.ApiException;
import com.spring.Outflearn.model.RefreshToken;
import com.spring.Outflearn.model.User;
import com.spring.Outflearn.repository.RefreshTokenRepository;
import com.spring.Outflearn.service.TokenService;
import com.spring.Outflearn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

//    @Override
//    @Transactional
//    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {
//
//      // 회원 정보 존재하는지 확인
//        User user = userService.findByUsername(userLoginRequestDto.getUsername());
//
//        // 회원 패스워드 일치 여부 확인
//        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
//            throw new ApiException(ApiErrorCode.LOGIN_FAIL);
//
//        // AccessToken, RefreshToken 발급
//        TokenDto tokenDto = jwtTokenProvider.createTokenDto(user.getId(), user.getRoles());
//
//        // RefreshToken 저장
//        RefreshToken refreshToken = RefreshToken.builder()
//                .user(user)
////                .accessToken(tokenDto.getAccessToken())
//                .refreshToken(tokenDto.getRefreshToken())
//                .build();
//
//        refreshTokenRepository.save(refreshToken);
//        return tokenDto;
//    }

    // Access Token 유효기간 만료 시 재발급
    @Override
    @Transactional
    public TokenDto refresh(TokenRequestDto tokenRequestDto) {

        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new ApiException(ApiErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        // AccessToken 에서 UserId (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        User user = (User) authentication.getPrincipal();
        if(user == null){
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        //  DB 에 일치하는 Refresh Token 이 없음
        RefreshToken refreshToken = refreshTokenRepository.findByUser_IdAndRefreshToken(user.getId(), tokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new ApiException(ApiErrorCode.INVALID_TOKEN));

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(user.getId(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateRefreshToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }

    @Override
    public TokenRequestDto setTokenRequestDto(String accessToken, String refreshToken) {
        TokenRequestDto tokenRequestDto = TokenRequestDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return tokenRequestDto;
    }
}
