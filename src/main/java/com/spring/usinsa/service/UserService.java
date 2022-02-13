package com.spring.usinsa.service;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;

public interface UserService {
    User findFirstByUsernameAndSocial(String username, Social social);
    User findFirstByEmail(String email);
    User findFirstByEmailAndName(String email, String name);
    User findFirstByEmailAndSocial(String email, Social social);
    User findFirstBySocialAndSocialId(Social social, String socialId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsBySocialAndSocialId(Social social, String socialId);
    User findById(Long userId);
    User signUp(User user);
    User resetPassword(Long userId, UserResetPasswordRequestDto userResetPasswordRequestDto);
}
