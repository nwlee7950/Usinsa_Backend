package com.spring.usinsa.dto.oauth2;

import com.spring.usinsa.model.Social;
import com.spring.usinsa.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@Setter
@ToString
public class KakaoProfile {
    private String id;
    private KakaoAccount kakao_account;

    @Getter
    @Setter
    @ToString
    public static class KakaoAccount {
        private String email;
        private Profile profile;
        private String age_range;
        private String birthday;
        private String birthyear;
        private String gender;
        private String phone_number;

        @Getter
        @Setter
        @ToString
        public static class Profile {
            private String nickname;
            private String profile_image_url;

        }
    }

    public User toUserEntity() {
        return User.builder()
                .email(this.kakao_account.email)
                .roles(Arrays.asList(User.Role.USER.getValue()))
                .social(Social.SOCIAL_KAKAO.getValue())
                .socialId(this.id)
                .build();
    }
}