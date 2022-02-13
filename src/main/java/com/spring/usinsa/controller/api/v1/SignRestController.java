package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.config.jwt.JwtTokenProvider;
import com.spring.usinsa.config.jwt.TokenDto;
import com.spring.usinsa.dto.UserLoginRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.TokenService;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Sign In / Sign Up"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignRestController {

    private final UserService userService;
    private final ApiResponseService apiResponseService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "아이디/비밀번호로 로그인합니다.")
    @PostMapping("/signin")
    public SingleResponse<TokenDto> login(
            @ApiParam(value = "로그인 요청 DTO", required = true)
            @ModelAttribute UserLoginRequestDto userLoginRequestDto) {

        // 로그인 검증 및 JWT 토큰 발급
        TokenDto tokenDto = tokenService.login(userLoginRequestDto);

        return apiResponseService.getSingleResult(tokenDto);
    }

    @ApiOperation(value = "회원가입", notes = "이메일, 비밀번호, 닉네임 설정")
    @PostMapping("/signup")
    public SingleResponse<TokenDto> signUp(
            @ApiParam(value = "회원가입 STEP 1 요청 DTO", required = true)
            @ModelAttribute UserSignUpRequestDto userSignUpRequestDto) {

        // 회원가입
        User savedUser = userService.signUp(userSignUpRequestDto.toUserEntity(passwordEncoder));

        // JWT 토큰 발급
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(savedUser.getId(), savedUser.getRole().getValue());

        return apiResponseService.getSingleResult(tokenDto);
    }
}
