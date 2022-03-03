package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ProductDetailDto;
import com.spring.usinsa.model.product.ProductDetail;

public interface ProductDetailService {
    ProductDetail save(ProductDetailDto.Request productDetailDto) throws Exception;
    ProductDetail findById(long id);
    ProductDetail updateContentImageById(ProductDetailDto.Request productDetailDto) throws Exception;
}
