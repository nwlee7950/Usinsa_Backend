package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.config.jwt.JwtTokenProvider;
import com.spring.usinsa.config.jwt.TokenDto;
import com.spring.usinsa.dto.EmailRequestDto;
import com.spring.usinsa.dto.EmailSendDto;
import com.spring.usinsa.dto.UserLoginRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.MailService;
import com.spring.usinsa.service.TokenService;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Api(tags = {"1. Sign In / Sign Up"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignRestController {

    private final MailService mailService;
    private final UserService userService;
    private final ApiResponseService apiResponseService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "로그인", notes = "이메일/비밀번호로 로그인합니다.")
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

        // 사용자 생성 및 아이디, 이메일, 비밀번호, 가입경로 설정
        User savedUser = userService.signUp(userSignUpRequestDto);

        // JWT 토큰 발급
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(savedUser.getId(), savedUser.getRoles());

        return apiResponseService.getSingleResult(tokenDto);
    }

    @ApiOperation(value = "이메일 인증", notes = "입력한 이메일로 인증코드 발송")
    @PostMapping("/verify-email")
    public CommonResponse verifyEmail(
            @ApiParam(value = "아이디 찾기 이메일 주소 요청 DTO", required = true)
            @ModelAttribute EmailRequestDto emailRequestDto) throws MessagingException {

        // 사용자 검색
        User user = userService.findFirstByEmail(emailRequestDto.getEmail());

        // 아이디 이메일 양식 설정
        EmailSendDto emailSendDto = mailService.setIdMailDto(emailRequestDto.getEmail(), user);

        // 이메일 발송
        mailService.sendMail(emailSendDto);

        return apiResponseService.getSuccessResult();
    }
}
