package com.spring.usinsa.service;

import com.spring.usinsa.dto.UserResetPasswordRequestDto;
import com.spring.usinsa.dto.UserSignUpRequestDto;
import com.spring.usinsa.model.User;

public interface UserService {
    User findByUsername(String username);
    User findFirstByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    User findById(long userId);
    User signUp(UserSignUpRequestDto userSignUpRequestDto);
    User resetPassword(User user, UserResetPasswordRequestDto userResetPasswordRequestDto);
}
