package com.spring.Outflearn.serviceImpl;

import com.spring.Outflearn.exception.ApiErrorCode;
import com.spring.Outflearn.exception.ApiException;
import com.spring.Outflearn.model.User;
import com.spring.Outflearn.repository.UserRepository;
import com.spring.Outflearn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USERNAME_NOT_FOUND));

        return user;
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
    public User findFirstByEmail(String email) {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new ApiException(ApiErrorCode.EMAIL_NOT_FOUND));

        return user;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
