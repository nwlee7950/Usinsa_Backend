package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.DeliveryInfoDto;
import com.spring.usinsa.model.product.DeliveryInfo;

public interface DeliveryInfoService {
    DeliveryInfo findById(long id);
    DeliveryInfo save(DeliveryInfoDto.Request deliveryInfoDto);
    void deleteById(long id);
}
