package com.spring.usinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBodyUpdateRequestDto {

    private Long height;    // 키
    private Long weight;    // 몸무게
}
