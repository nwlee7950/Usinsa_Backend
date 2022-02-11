package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.model.VerificationCode;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.service.VerificationCodeService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"5. User "})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;
    private final ApiResponseService apiResponseService;
    private final VerificationCodeService verificationCodeService;

    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호 재설정 이메일을 발송했을 때 같이 보냈던 code 가 유효하면 새로운 비밀번호로 재설정")
    @PostMapping("/reset-password")
    public CommonResponse updatePassword(@ModelAttribute UserResetPasswordRequestDto userResetPasswordRequestDto) {

        // 코드 검증
        VerificationCode verificationCode = verificationCodeService.verifyCode(userResetPasswordRequestDto.getCode());

        // 사용자 비밀번호 재설정
        userService.resetPassword(verificationCode.getUserId(), userResetPasswordRequestDto);

        return apiResponseService.getSuccessResult();
    }
}
