package com.spring.outflearn.controller.api.v1;

import com.spring.outflearn.config.jwt.JwtTokenProvider;
import com.spring.outflearn.config.jwt.TokenDto;
import com.spring.outflearn.config.jwt.TokenRequestDto;
import com.spring.outflearn.response.ApiResponseService;
import com.spring.outflearn.response.SingleResponse;
import com.spring.outflearn.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"2. Token refresh"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class TokenRestController {

    private final ApiResponseService apiResponseService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "토큰 재발급", notes = "Access Token 만료 시 자동으로 재발급 시켜줍니다.")
    @PostMapping("/refresh")
    public SingleResponse<TokenDto> refresh(HttpServletRequest request) {

        // Access Token, Refresh Token 값 추출
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        // TokenRequestDto 생성
        TokenRequestDto tokenRequestDto = tokenService.setTokenRequestDto(accessToken, refreshToken);

        // Refresh 토큰 검증 및 Access Token 재발급
        TokenDto tokenDto = tokenService.refresh(tokenRequestDto);

        return apiResponseService.getSingleResult(tokenDto);
    }

}
