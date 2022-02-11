package com.spring.usinsa.service;

import com.spring.usinsa.dto.UserBodyUpdateRequestDto;
import com.spring.usinsa.model.UserProfile;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfile findByUserId(long userId);
    UserProfile save(UserProfile userProfile);
    UserProfile upsertUserBody(UserProfile userProfile, UserBodyUpdateRequestDto userBodyUpdateRequestDto);
    UserProfile upsertUserProfileImage(UserProfile userProfile, MultipartFile multipartFile) throws Exception;;
}
