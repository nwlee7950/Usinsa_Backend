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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Long productDetailId;     // 상품 아이디
    private String image;       // 이미지 파일 이름
}
