package com.spring.usinsa.service;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.model.User;

public interface UserService {
    User findByUsernameAndSocial(String username, String social);
    User findFirstByEmail(String email);
    User findFirstByEmailAndSocial(String email, String social);
    User findFirstBySocialAndSocialId(String social, String socialId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsBySocialAndSocialId(String social, String socialId);
    User save(User user);
    User findById(long userId);
    User signUp(UserSignUpRequestDto userSignUpRequestDto);
    User resetPassword(User user, UserResetPasswordRequestDto userResetPasswordRequestDto);
}
