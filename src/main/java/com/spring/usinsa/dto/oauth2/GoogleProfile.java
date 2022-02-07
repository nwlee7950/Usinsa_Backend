package com.spring.usinsa.dto.oauth2;

// AccessToken을 활용해 JWT의 Payload 부분인 사용자 정보를 Response받는 VO

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleProfile {

    private String iss;     // 토큰의 발급자
    private String azp;     // 토큰의 대상자
    private String aud;     // 토큰의 대상자
    private String sub;     // 토큰의 제목   -> Primary Key 로 사용
    private String hd;      // 이메일 발급지 (ex - jae518@naver.com -> www.naver.com)
    private String email;
    private String emailVerified;
    private String atHash;
    private String name;
    private String picture;
    private String givenName;
    private String familyName;
    private String locale;
    private String iat;     // 토큰 발급 시간
    private String exp;     // 토큰 만료 시간
    private String alg;     // 암호화 알고리즘
    private String kid;
    private String typ;     // JWT 토큰 형식인지 세션인지 쿠키인지

}