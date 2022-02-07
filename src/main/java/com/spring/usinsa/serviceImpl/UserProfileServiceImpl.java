package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.dto.UserProfileResponseDto;
import com.spring.usinsa.dto.UserProfileUpdateRequestDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.UserProfile;
import com.spring.usinsa.repository.UserProfileRepository;
import com.spring.usinsa.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final String PROFILE_FOLDER = "profile/";

    @Override
    public UserProfile findByUserId(long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USERID_NOT_FOUND));
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfileResponseDto buildResponseDto(UserProfile userProfile) {

        return UserProfileResponseDto.builder()
                .profileImage(userProfile.getProfileImage())
                .nickname(userProfile.getNickname())
                .job(userProfile.getJob())
                .email(userProfile.getEmail())
                .introduction(userProfile.getIntroduction())
                .build();
    }

    @Override
    @Transactional
    public UserProfile updateUserProfile(UserProfile userProfile, UserProfileUpdateRequestDto userProfileUpdateRequestDto) {

        if(userProfileUpdateRequestDto.getNickname() != null)
            userProfile.setNickname(userProfileUpdateRequestDto.getNickname());

        if(userProfileUpdateRequestDto.getJob() != null)
            userProfile.setJob(userProfileUpdateRequestDto.getJob());

        if(userProfileUpdateRequestDto.getEmail() != null)
            userProfile.setEmail(userProfileUpdateRequestDto.getEmail());

        if(userProfileUpdateRequestDto.getIntroduction() != null)
            userProfile.setIntroduction(userProfileUpdateRequestDto.getIntroduction());

        return userProfileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfile updateUserProfileImage(UserProfile userProfile, MultipartFile multipartFile) throws Exception {

//        // 프로필 이미지 Upsert ( 기존 프로필 이미지가 있다면 삭제 후 재업로드 )
//        String upsertedProfileImage = minioService.upsertFile(userProfile.getProfileImage(), PROFILE_FOLDER, multipartFile);
//
//        // 프로필 이미지를 방금 업로드한 새로운 이미지로 설정
//        userProfile.setProfileImage(upsertedProfileImage);
//
//        return userProfileRepository.save(userProfile);
        return null;
    }

}
