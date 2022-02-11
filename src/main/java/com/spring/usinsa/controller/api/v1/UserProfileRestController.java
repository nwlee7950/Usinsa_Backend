package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.UserBodyUpdateRequestDto;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.UserProfile;
import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.UserProfileService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"5. User Profile "})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-profiles")
public class UserProfileRestController {

    private final UserProfileService userProfileService;
    private final ApiResponseService apiResponseService;

    @ApiOperation(value = "사용자 프로필 정보 출력", notes = "사용자 프로필 정보를 출력합니다.")
    @GetMapping
    public SingleResponse<UserProfile> getUserProfile(@AuthenticationPrincipal User user) {

        // 사용자 프로필 정보
        UserProfile userProfile = userProfileService.findByUserId(user.getId());

        return apiResponseService.getSingleResult(userProfile);
    }

    @ApiOperation(value = "사용자 키/몸무게 등록 및 수정", notes = "사용자 키/몸무게 정보를 등록 및 수정합니다.")
    @PostMapping
    public SingleResponse<UserProfile> updateUserBody(@AuthenticationPrincipal User user,
                                                                    @ApiParam(value = "사용자 프로필 수정 요청 DTO", required = true)
                                                                    @ModelAttribute UserBodyUpdateRequestDto userBodyUpdateRequestDto) {

        // 사용자 키/몸무게 정보
        UserProfile userProfile = userProfileService.findByUserId(user.getId());

        // 사용자 키/몸무게 정보 등록/수정
        UserProfile updatedUserProfile = userProfileService.upsertUserBody(userProfile, userBodyUpdateRequestDto);

        return apiResponseService.getSingleResult(updatedUserProfile);
    }

    @ApiOperation(value = "사용자 프로필 이미지 등록 및 수정", notes = "사용자 프로필 이미지를 등록 및 수정합니다.")
    @PostMapping("/images")
    public SingleResponse<UserProfile> upsertUserProfileImage(@AuthenticationPrincipal User user,
                                                              @ApiParam(value = "사용자 프로필 사진", required = true) MultipartFile profileImage) throws Exception {

        // 사용자 프로필
        UserProfile userProfile = userProfileService.findByUserId(user.getId());

        // 프로필 이미지 수정
        UserProfile updatedUserProfile = userProfileService.upsertUserProfileImage(userProfile, profileImage);

        return apiResponseService.getSingleResult(updatedUserProfile);
    }
}
