package com.spring.usinsa.service;

import com.spring.usinsa.dto.UserProfileResponseDto;
import com.spring.usinsa.dto.UserProfileUpdateRequestDto;
import com.spring.usinsa.model.UserProfile;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfile findByUserId(long userId);
    UserProfile save(UserProfile userProfile);
    UserProfileResponseDto buildResponseDto(UserProfile userProfile);
    UserProfile updateUserProfile(UserProfile userProfile, UserProfileUpdateRequestDto userProfileUpdateRequestDto);
    UserProfile updateUserProfileImage(UserProfile userProfile, MultipartFile multipartFile) throws Exception;;
}
