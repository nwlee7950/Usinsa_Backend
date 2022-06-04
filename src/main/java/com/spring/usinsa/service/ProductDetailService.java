package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ProductDetailDto;
import com.spring.usinsa.model.product.ProductDetail;

public interface ProductDetailService {
    ProductDetail save(ProductDetailDto.Request productDetailDto) throws Exception;
    ProductDetailDto.Response findById(Long id);
    void deleteById(Long id);
    void deleteByProductId(Long id);
}
