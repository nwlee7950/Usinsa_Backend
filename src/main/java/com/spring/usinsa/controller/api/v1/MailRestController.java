package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.EmailSendDto;
import com.spring.usinsa.dto.FindUsernameRequestDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.VerificationCode;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.service.MailService;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.service.VerificationCodeService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Api(tags = {"3. Send Email "})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mail")
public class MailRestController {

    private final UserService userService;
    private final MailService mailService;
    private final ApiResponseService apiResponseService;
    private final VerificationCodeService verificationCodeService;

    @ApiOperation(value = "아이디 찾기", notes = "입력한 이메일로 아이디 발송")
    @PostMapping("/username")
    public CommonResponse findUsername(
            @ApiParam(value = "아이디 찾기 요청 DTO", required = true)
            @ModelAttribute FindUsernameRequestDto findUsernameRequestDto) throws MessagingException {

        // 실명, 이메일로 사용자 검색
        User user = userService.findFirstByEmailAndName(findUsernameRequestDto.getEmail(), findUsernameRequestDto.getName());
        
        // 유신사에서 가입한 사용자가 아니라면 비밀번호 재설정 거부
        if(!user.getSocial().equals(Social.USINSA.getValue()))
            throw new ApiException(ApiErrorCode.NOT_USINSA_USER);
        
        // 아이디 이메일 양식 설정
        EmailSendDto emailSendDto = mailService.setIdMailDto(findUsernameRequestDto.getEmail(), user.getUsername());

        // 이메일 발송
        mailService.sendMail(emailSendDto);

        return apiResponseService.getSuccessResult();
    }

    @ApiOperation(value = "url, code 이메일 발송", notes = "가입한 이메일로 비밀번호 재설정이 가능한 url, code 발송")
    @PostMapping("/reset-password")
    public CommonResponse resetPassword(@RequestParam String username) throws MessagingException {

        // 사용자 검색
        User user = userService.findFirstByUsernameAndSocial(username, Social.USINSA);

        // 검증 코드 생성
        VerificationCode verificationCode = verificationCodeService.createCode(user.getId());

        // 비밀번호 이메일 양식 설정
        EmailSendDto emailSendDto = mailService.setPwMailDto(user.getEmail(), verificationCode.getCode());

        // 이메일 발송
        mailService.sendMail(emailSendDto);

        return apiResponseService.getSuccessResult();
    }
}
