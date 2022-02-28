package com.spring.usinsa.model.product;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deliveryCompany;     // 배송사 명
    private String domesticOrOverseas;  // 국내, 해외 배송 구분
    private String storeDelivery;       // 입점사 배송?
    private int deliveryFee;            // 배송비
    private int howMuchMorefreeFee;     // 얼마 이상 구매 시 배송비 무료
}
