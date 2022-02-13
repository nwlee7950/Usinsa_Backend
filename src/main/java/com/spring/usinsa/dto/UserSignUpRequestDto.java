package com.spring.usinsa.dto;

import com.spring.usinsa.model.Role;
import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDto {

    private String username;    // 아이디
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String phone;

    public User toUserEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(this.username)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .social(Social.USINSA)
                .disable(false)
                .role(Role.ROLE_USER)
                .password(passwordEncoder.encode(this.password))
                .nickname(this.nickname)
                .build();
    }

}
