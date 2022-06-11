package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Review;
import lombok.*;

public class ReviewDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Long userId; //동일한 상품에 review를 1번만 달아야하므로
        private Long productId; //상품 ID
        private int point; //별점
        private String body; //리뷰내용
        private String nickname; // 닉네임

        public Review toReviewEntity(){
            return Review.builder()
                    .userId(this.userId)
                    .productId(this.productId)
                    .point(this.point)
                    .body(this.body)
                    .nickname(this.nickname)
                    .build();
        }
    }


    @Getter
    @Builder
    public static class Response{
        private Long id;
        private Long userId; //동일한 상품에 review를 1번만 달아야하므로
        private Long productId; //상품 ID
        private int point; //별점
        private String body; //리뷰내용
        private String nickname; //닉네임

        public Response(Long id, Long userId, Long productId, int point, String body, String nickname){
            this.id = id;
            this.userId = userId;
            this.productId = productId;
            this.point = point;
            this.body = body;
            this.nickname = nickname;
        }

        public static ReviewDto.Response toReviewDtoResponse(Review review){
            return Response.builder()
                    .id(review.getId())
                    .userId(review.getUserId())
                    .productId(review.getProductId())
                    .point(review.getPoint())
                    .body(review.getBody())
                    .nickname(review.getNickname())
                    .build();
        }
    }

}

