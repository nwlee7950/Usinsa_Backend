package com.spring.Outflearn.serviceImpl;

import com.spring.Outflearn.exception.ApiErrorCode;
import com.spring.Outflearn.exception.ApiException;
import com.spring.Outflearn.model.User;
import com.spring.Outflearn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long userId = -1;
        try {
            userId = Long.parseLong(username);
        }
        catch (Exception e){
            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.USERNAME_NOT_FOUND));

        return user;
    }
}
