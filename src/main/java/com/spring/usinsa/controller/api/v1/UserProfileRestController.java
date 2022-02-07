package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.UserProfileResponseDto;
import com.spring.usinsa.dto.UserProfileUpdateRequestDto;
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

    @ApiOperation(value = "사용자 프로필 정보 출력, 미리보기 화면", notes = "사용자 프로필 정보(username, 프로필, 관심분야) 를 출력합니다. (미리보기 화면으로도 사용)")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//    })
    @GetMapping
    public SingleResponse<UserProfileResponseDto> getUserProfile(@AuthenticationPrincipal User user) {

        // 사용자 프로필 정보
        UserProfile userProfile = userProfileService.findByUserId(user.getId());

        // 우선 UserProfile 정보만 ResponseDto 에 빌드
        UserProfileResponseDto userProfileResponseDto = userProfileService.buildResponseDto(userProfile);

        // 상단의 '내 주소' 에 들어갈 사용자 정보 빌드
        userProfileResponseDto.setUser(user);

        return apiResponseService.getSingleResult(userProfileResponseDto);
    }

    @ApiOperation(value = "사용자 프로필 등록 및 수정", notes = "사용자 프로필 정보를 등록 및 수정합니다.")
    @PostMapping
    public SingleResponse<UserProfile> updateUserProfile(@AuthenticationPrincipal User user,
                                                                    @ApiParam(value = "사용자 프로필 수정 요청 DTO", required = true)
                                                                    @ModelAttribute UserProfileUpdateRequestDto userProfileUpdateRequestDto) {

        // 사용자 프로필 정보
        UserProfile userProfile = userProfileService.findByUserId(user.getId());

        // 사용자 프로필 정보 수정
        UserProfile updatedUserProfile = userProfileService.updateUserProfile(userProfile, userProfileUpdateRequestDto);

        return apiResponseService.getSingleResult(updatedUserProfile);
    }

    @ApiOperation(value = "사용자 프로필 이미지 등록 및 수정", notes = "사용자 프로필 이미지를 등록 및 수정합니다.")
    @PostMapping("/images")
    public SingleResponse<UserProfile> updateUserProfileImage(@AuthenticationPrincipal User user,
                                                                    @ApiParam(value = "사용자 프로필 사진", required = true) MultipartFile profileImage) throws Exception {

        // 사용자 프로필 정보
        UserProfile userProfile = userProfileService.findByUserId(user.getId());

        // 사용자 프로필 이미지 수정
        UserProfile updatedUserProfile = userProfileService.updateUserProfileImage(userProfile, profileImage);

        return apiResponseService.getSingleResult(updatedUserProfile);
    }
}
