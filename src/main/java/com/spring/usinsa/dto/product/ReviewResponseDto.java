package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private Long productId;
    private int point;
    private String body;
    private String nickname;

    public static ReviewResponseDto toReviewResponseDto(Review review){
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .productId(review.getProductId())
                .point(review.getPoint())
                .body(review.getBody())
                .nickname(review.getNickname())
                .build();
    }
}
