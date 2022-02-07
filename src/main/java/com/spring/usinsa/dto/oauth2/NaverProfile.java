package com.spring.usinsa.dto.oauth2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverProfile {
    private String resultcode;
    private String message;
    private Response response;

    @Getter
    @Setter
    @ToString
    public static class Response {
        private String id;      // socialId
        private String name;    // 실명
        private String profile_image;
        private String age;     // 연령대 (ex) "20~29"
        private String gender;  // 성별 (ex) "M", "F"
        private String email;   // 가입 이메일
        private String mobile;  // 핸드폰 번호 (ex) "010-8332-0930"
        private String birthday;   // 생일 (ex) "05-18"
        private String birthyear;   // 출생연도 (ex) "1996"
    }
}
