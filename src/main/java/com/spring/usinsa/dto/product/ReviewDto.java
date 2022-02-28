package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ReviewDto {

    @Getter
    public static class Request{
        private Long id;
        private Long userId;
        private Long productId;
        private int weight;
        private int height;
        private String gender;
        private int point;
        private String body;

        public Review toReviewEntity(){
            return Review.builder()
                    .body(body)
                    .gender(gender)
                    .weight(weight)
                    .height(height)
                    .point(point)
                    .productId(productId).build();
        }
    }

    @Getter
    public static class FindRequest{
        private Long productId;
        private Long userId;
        private String sort;
        private int page;
    }

}
