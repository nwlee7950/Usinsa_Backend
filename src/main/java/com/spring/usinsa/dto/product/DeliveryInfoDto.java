package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.DeliveryInfo;
import lombok.Getter;
import lombok.Setter;

public class DeliveryInfoDto {

    @Getter
    @Setter
    public static class Request{
        private String deliveryCompany;
        private int deliveryFee;
        private int howMuchMoreFreeFee;   // 얼마 이상이면 무료 배송
        private String domesticDelivery;
        private String storeDelivery;

        public DeliveryInfo toDeliveryInfoEntity(){
            return DeliveryInfo.builder()
                    .deliveryCompany(this.deliveryCompany)
                    .deliveryFee(this.deliveryFee)
                    .howMuchMorefreeFee(this.howMuchMoreFreeFee)
                    .domesticOrOverseas(this.domesticDelivery)
                    .storeDelivery(this.storeDelivery).build();

        }
    }
}
