package com.spring.usinsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Social {

    SOCIAL_USINSA("usinsa"),
    SOCIAL_KAKAO("kakao"),
    SOCIAL_NAVER("naver"),
    SOCIAL_GOOGLE("google");

    private String value;
}
