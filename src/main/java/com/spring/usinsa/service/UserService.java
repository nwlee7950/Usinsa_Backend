package com.spring.usinsa.service;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;

public interface UserService {
    User findFirstByUsernameAndSocial(String username, String social);
    User findFirstByEmail(String email);
    User findFirstByEmailAndName(String email, String name);
    User findFirstByEmailAndSocial(String email, String social);
    User findFirstBySocialAndSocialId(String social, String socialId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsBySocialAndSocialId(String social, String socialId);
    User save(User user);
    User findById(Long userId);
    User signUp(UserSignUpRequestDto userSignUpRequestDto);
    User resetPassword(Long userId, UserResetPasswordRequestDto userResetPasswordRequestDto);
}
