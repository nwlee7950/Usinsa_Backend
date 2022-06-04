package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private Long productId;
    private int point;
    private String body;
    private String nickname;

    public Review toReviewEntity(Long userId, String nickname){
        return Review.builder()
                .body(body)
                .point(point)
                .userId(userId)
                .nickname(nickname)
                .productId(productId).build();
    }
}
