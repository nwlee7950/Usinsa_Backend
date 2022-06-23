package com.spring.usinsa.model;

import com.spring.usinsa.model.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 결제상태. ready:미결제, paid:결제완료, cancelled:결제취소, failed:결제실패
    private String status;

    // 결제 번호
    private String impUid;

    // 주문 번호
    private String merchantUid;

    // samsung : 삼성페이 / card : 신용카드 / trans : 계좌이체 / vbank : 가상계좌 / phone : 휴대폰 / cultureland : 문화상품권 / smartculture : 스마트문상 / booknlife : 도서문화상품권 / happymoney : 해피머니 / point : 포인트 / ssgpay : SSGPAY / lpay : LPAY / payco : 페이코 / kakaopay : 카카오페이 / tosspay : 토스 / naverpay : 네이버페이
    private String payMethod;

    // 총 결제금액
    private long amount;

    // 주문 수량
    private int quantity;

    // 사이즈
    private String size;
}
