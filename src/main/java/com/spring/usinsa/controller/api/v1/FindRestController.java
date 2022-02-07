package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.EmailRequestDto;
import com.spring.usinsa.dto.EmailSendDto;
import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.VerificationCode;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.service.MailService;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.service.VerificationCodeService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Api(tags = {"3. Find User ID, PW "})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FindRestController {

    private final UserService userService;
    private final MailService mailService;
    private final ApiResponseService apiResponseService;
    private final VerificationCodeService verificationCodeService;

    @ApiOperation(value = "비밀번호 변경 url, secret code 발송", notes = "가입한 이메일로 비밀번호 수정이 가능한 url, secret code 발송")
    @PostMapping("/find-password")
    public CommonResponse findPassword(EmailRequestDto emailRequestDto) throws MessagingException {

        // 사용자 검색
        User user = userService.findFirstByEmail(emailRequestDto.getEmail());

        // 검증 코드 생성
        VerificationCode verificationCode = verificationCodeService.createCode(user);

        // 비밀번호 이메일 양식 설정
        EmailSendDto emailSendDto = mailService.setPwMailDto(emailRequestDto.getEmail(), verificationCode.getCode());

        // 이메일 발송
        mailService.sendMail(emailSendDto);

        return apiResponseService.getSuccessResult();
    }

    @ApiOperation(value = "secret code 검증", notes = "비밀번호 수정 url 을 클릭했을 때 같이 보냈던 secret code 검증")
    @GetMapping("/verify-code")
    public CommonResponse verifyCode(@RequestParam("code") String code) {

        // 코드 검증
        VerificationCode verificationCode = verificationCodeService.verifyCode(code);

        return apiResponseService.getSuccessResult();
    }

    @ApiOperation(value = "비밀번호 재설정", notes = "새로운 비밀번호 설정")
    @PostMapping("/reset-password")
    public CommonResponse updatePassword(@ModelAttribute UserResetPasswordRequestDto userResetPasswordRequestDto) {

        // 코드 검증
        VerificationCode verificationCode = verificationCodeService.verifyCode(userResetPasswordRequestDto.getCode());

        // 사용자 비밀번호 재설정
        User user = userService.resetPassword(verificationCode.getUser(), userResetPasswordRequestDto);

        return apiResponseService.getSuccessResult();
    }
}
