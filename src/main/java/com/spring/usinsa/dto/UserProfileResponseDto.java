package com.spring.usinsa.dto;

import com.spring.usinsa.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDto {

    private User user;

    private String profileImage;
    private String nickname;
    private String job;
    private String email;
    private String introduction;
}
