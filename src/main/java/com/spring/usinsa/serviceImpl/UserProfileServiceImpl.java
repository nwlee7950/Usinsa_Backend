package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.dto.UserBodyUpdateRequestDto;
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
    @Transactional
    public UserProfile upsertUserBody(UserProfile userProfile, UserBodyUpdateRequestDto userBodyUpdateRequestDto) {

        userProfile.setHeight(userBodyUpdateRequestDto.getHeight());
        userProfile.setWeight(userBodyUpdateRequestDto.getWeight());

        return userProfileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfile upsertUserProfileImage(UserProfile userProfile, MultipartFile multipartFile) throws Exception {

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
