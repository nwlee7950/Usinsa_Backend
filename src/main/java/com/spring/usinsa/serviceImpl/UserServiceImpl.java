package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.VerificationCode;
import com.spring.usinsa.repository.UserRepository;
import com.spring.usinsa.service.UserProfileService;
import com.spring.usinsa.service.UserService;
import com.spring.usinsa.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileService userProfileService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;

    @Override
    public User findByUsernameAndSocial(String username,String social) {
        User user = userRepository.findByUsernameAndSocial(username, social)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USERNAME_NOT_FOUND));

        return user;
    }

    @Override
    public User findFirstByEmail(String email) {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new ApiException(ApiErrorCode.EMAIL_NOT_FOUND));

        return user;
    }

    @Override
    public User findFirstByEmailAndSocial(String email, String social) {
        User user = userRepository.findFirstByEmailAndSocial(email, social)
                .orElseThrow(() -> new ApiException(ApiErrorCode.EMAIL_NOT_FOUND));

        return user;
    }

    @Override
    public User findFirstBySocialAndSocialId(String social, String socialId) {
        User user = userRepository.findFirstBySocialAndSocialId(social, socialId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.EMAIL_NOT_FOUND));

        return user;
    }


    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsBySocialAndSocialId(String social, String socialId) {
        return userRepository.existsBySocialAndSocialId(social, socialId);
    }


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USERID_NOT_FOUND));

        return user;
    }

    @Override
    public User signUp(UserSignUpRequestDto userSignUpRequestDto) {
        return null;
    }

    @Override
    public User resetPassword(User user, UserResetPasswordRequestDto userResetPasswordRequestDto) {

        // 새로운 비밀번호, 새로운 비밀번호 확인 값 검사
        if(!userResetPasswordRequestDto.getNew_password().equals(userResetPasswordRequestDto.getNew_password_confirm())) {
            throw new ApiException(ApiErrorCode.PASSWORD_NOT_EQUAL);
        }

        // 새로운 비밀번호로 사용자 비밀번호 재설정
        user.setPassword((passwordEncoder.encode(userResetPasswordRequestDto.getNew_password())));

        // 사용자 저장
        User savedUser = save(user);

        VerificationCode verificationCode = verificationCodeService.findByCode(userResetPasswordRequestDto.getCode());

        // 기존 Verification Code 삭제
        verificationCodeService.delete(verificationCode);

        return savedUser;
    }

}
