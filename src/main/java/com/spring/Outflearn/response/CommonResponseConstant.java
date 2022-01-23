package com.spring.Outflearn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponseConstant {
    SUCCESS(0, "성공하였습니다."),
    FAIL(-1, "실패하였습니다.");

    private int code;
    private String msg;
}
