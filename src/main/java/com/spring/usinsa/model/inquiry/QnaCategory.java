package com.spring.usinsa.model.inquiry;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QnaCategory {

    EXCHANEGE("교환"),
    REFUND("환불");

    String value;

}
