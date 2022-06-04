package com.spring.usinsa.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewBodyUpdateRequestDto {
    private Long reviewId;
    private String body;
    private int point;
}
