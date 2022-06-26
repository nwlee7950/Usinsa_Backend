package com.spring.usinsa.dto;

import com.spring.usinsa.model.Payment;
import com.spring.usinsa.model.User;
import com.spring.usinsa.model.product.Product;
import lombok.*;

public class PaymentDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long productId;
        private Long userId;
        private String status;
        private String merchantUid;
        private String payMethod; // samsung : 삼성페이 / card : 신용카드 / trans : 계좌이체 / vbank : 가상계좌 / phone : 휴대폰 / cultureland : 문화상품권 / smartculture : 스마트문상 / booknlife : 도서문화상품권 / happymoney : 해피머니 / point : 포인트 / ssgpay : SSGPAY / lpay : LPAY / payco : 페이코 / kakaopay : 카카오페이 / tosspay : 토스 / naverpay : 네이버페이
        private long amount; // 총 결제금액
        private int quantity; // 주문 수량
        private String size; // 사이즈

        public Payment toPaymentEntity(Product product, User user) {
            return Payment.builder()
                    .product(product)
                    .user(user)
                    .status(this.status)
                    .merchantUid(this.merchantUid)
                    .payMethod(this.payMethod)
                    .amount(this.amount)
                    .quantity(this.quantity)
                    .size(this.size)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest{
        private String impUid; // 결제 번호
        private String merchantUid; // 주문 번호
    }

    @Getter
    @Setter
    public static class WebhookRequest{
        private String impUid; // 결제 번호
        private String merchantUid; // 주문 번호
        private String status; //상태
    }

    @Getter
    @Builder
    public static class Response {
        private Long productId;
        private Long userId;
        private String status;
        private String impUid; // 결제 번호
        private String merchantUid; // 주문 번호
        private String payMethod; // samsung : 삼성페이 / card : 신용카드 / trans : 계좌이체 / vbank : 가상계좌 / phone : 휴대폰 / cultureland : 문화상품권 / smartculture : 스마트문상 / booknlife : 도서문화상품권 / happymoney : 해피머니 / point : 포인트 / ssgpay : SSGPAY / lpay : LPAY / payco : 페이코 / kakaopay : 카카오페이 / tosspay : 토스 / naverpay : 네이버페이
        private long amount; // 총 결제금액
        private int quantity; // 주문 수량
        private String size; // 사이즈

        public static PaymentDto.Response toPaymentDtoResponse(Payment payment){
            return Response.builder()
                    .productId(payment.getProduct().getId())
                    .userId(payment.getUser().getId())
                    .status(payment.getStatus())
                    .impUid(payment.getImpUid())
                    .merchantUid(payment.getMerchantUid())
                    .payMethod(payment.getPayMethod())
                    .amount(payment.getAmount())
                    .quantity(payment.getQuantity())
                    .size(payment.getSize())
                    .build();
        }
    }
}
