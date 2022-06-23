package com.spring.usinsa.dto;

import lombok.*;

public class PaymentCancelDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String imp_uid; // 결제 번호
    }

    @Setter
    @Getter
    @Builder
    public static class Response {
        private String imp_uid; // 결제 번호
    }
}
