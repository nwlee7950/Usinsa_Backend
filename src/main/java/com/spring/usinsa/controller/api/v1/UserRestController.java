package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.UserProfile;
import com.spring.usinsa.model.VerificationCode;
import com.spring.usinsa.response.CommonResponse;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.service.VerificationCodeService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"5. User "})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
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

    @ApiOperation(value = "사용자 프로필 정보 출력", notes = "사용자 프로필 정보를 출력합니다.")
    @GetMapping
    public SingleResponse<User> getUser(@AuthenticationPrincipal User user) {

        // 사용자 프로필 정보
        User findUser = userService.findById(user.getId());

        return apiResponseService.getSingleResult(findUser);
    }
}
