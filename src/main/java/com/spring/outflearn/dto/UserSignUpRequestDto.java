package com.spring.outflearn.dto;

import com.spring.outflearn.model.User;
import lombok.*;

import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDto {

    private String email;
    private String password;

    public User toUserEntity() {
        return User.builder()
                .email(this.email)
                .roles(Collections.singletonList("ROLE_USER"))
//                .password(this.password)
//                .nickname(this.email)
                .build();
    }

}
