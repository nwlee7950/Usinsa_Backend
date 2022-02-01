package com.spring.outflearn.exception;

import lombok.Getter;

@Getter
public enum ApiErrorCode {
    //COMMON
    INVALID_PARAMS(1000, "잘못된 요청입니다."),
    NEED_(1002, "서버에서 요청을 처리하는 도중 오류가 발생하였습니다."),
    SERVER_TRANSACTION_ERROR(1001, "서버에서 요청을 처리하는 도중 오류가 발생하였습니다."),
    LOGIN_FAIL(1003, "아이디 또는 비밀번호가 일치하지 않습니다."),
    SQL_INVALID_QUERY(1004, "작업을 처리하는 도중 서버에서 오류가 발생하였습니다. 지속적인 오류가 발생할 시 관리자에게 문의해주시기 바랍니다."),
    MAIL_SEND_FAIL(1005, "메일 발송에 실패하였습니다. 다시 시도해주세요."),
    INVALID_ENTITY_FIELD(1006, "올바른 양식이 아닙니다. 다시 시도해주세요."),

    // SIGN UP
    SIGNUP_USERNAME_DUPLICATED(2000, "이미 사용 중인 아이디입니다."),
    SIGNUP_EMAIL_DUPLICATED(2001, "이미 사용 중인 이메일입니다."),
    SIGNUP_INVALID_PASSWORD(2002, "양식에 맞지 않는 비밀번호입니다."),
    SIGNUP_INCORRECT_PASSWORD(2003, "비밀번호가 일치하지 않습니다."),

    // TOKEN
    TOKEN_ROLE_EMPTY(3000, "사용자 권한을 확인할 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(3001, "로그인 유지시간이 만료되었습니다. 다시 로그인 해주세요."),
    INVALID_TOKEN(3002, "토큰이 유효하지 않습니다. 다시 로그인 해주세요."),
    TOKEN_REFRESH(3003, "토큰 재발급 요청"),

    // USER
    USERNAME_NOT_FOUND(4000, "입력하신 아이디로 가입된 유저가 없습니다."),
    USERID_NOT_FOUND(4001, "user id 로 User 를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(4002, "입력하신 이메일로 가입된 유저가 없습니다."),
    PASSWORD_NOT_EQUAL(4003, "비밀번호가 서로 일치하지 않습니다."),
    VERIFICATION_CODE_EXPIRED(4004, "비밀번호 재설정 유효 시간이 초과되었습니다. 다시 시도해주세요."),

    // REGISTER_ROUTE
    REGISTER_ROUTE_NOT_FOUND(5000, "해당 ID를 가진 RegisterRoute 가 없습니다."),
    REGISTER_ROUTE_EMPTY(5001, "가입 경로를 선택해주세요."),

    // PLAN
    INVALID_PLAN(6000, "플랜을 다시 선택해주세요."),

    TEMP(0, "TEMP");


    private final int statusCode;
    private final String message;

    ApiErrorCode(final int statusCode, final String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.message;
    }
}