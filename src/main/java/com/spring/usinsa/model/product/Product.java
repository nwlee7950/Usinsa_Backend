package com.spring.usinsa.model.product;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 상품명
    private int price;    // 가격
    private String gender;  // 상품 성별

    private String titleImage;    // 상품 대표 이미지
    private String contentImage;    // 상품 본문 이미지
    private String content;     // 상품 본문 내용

    private Long discountStartDate;  // 세일 시작 기간
    private Long discountEndDate;    // 세일 종료 기간
    private Long createdAt;          // 상품 등록 시간

    @ManyToOne
    @JoinColumn(name = "subCategory_id", insertable = false, updatable = false)
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryInfo_id", insertable = false, updatable = false)
    private DeliveryInfo deliveryInfo;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "productId")
    private List<ProductDiscountDetail> productDisCountDetail;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "productId")
    private List<ProductImage> productImages;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "productId")
    private List<ProductSize> productSize;
}
