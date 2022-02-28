package com.spring.usinsa.model.product;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 아이디
    private Long productId;     // 상품 아이디
    private String detailImageList;     // 상품 상세 이미지 리스트 경로 (ex - 같은 모델이지만 색상별로 다른 사진을 가지고 있음)
    private String contentImage;       // 상품 본문 이미지 경로
}
