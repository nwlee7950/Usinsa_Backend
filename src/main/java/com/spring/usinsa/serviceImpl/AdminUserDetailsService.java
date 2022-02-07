package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import com.spring.usinsa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // email에 해당하는 정보를 데이터베이스에서 읽어 Member 객체에 저장한다.
        User user = userService.findByUsernameAndSocial(username, Social.SOCIAL_USINSA.getValue());

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return user;
    }
}
