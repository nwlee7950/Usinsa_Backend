package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.DeliveryInfoDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.DeliveryInfo;
import com.spring.usinsa.repository.DeliveryInfoRepository;
import com.spring.usinsa.service.DeliveryInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

    DeliveryInfoRepository deliveryInfoRepository;

    @Override
    public DeliveryInfo save(DeliveryInfoDto.Request deliveryInfoDto) {
        return deliveryInfoRepository.save(deliveryInfoDto.toDeliveryInfoEntity());
    }

    @Override
    public DeliveryInfo findById(long id) {
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.DeliveryInfo_NOT_FOUND));

        return deliveryInfo;
    }

    @Override
    public void deleteById(long id) {
        deliveryInfoRepository.deleteById(id);
    }
}
