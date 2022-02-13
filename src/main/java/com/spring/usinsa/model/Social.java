package com.spring.usinsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Social {

    USINSA("usinsa"),
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google");

    private String value;
}
