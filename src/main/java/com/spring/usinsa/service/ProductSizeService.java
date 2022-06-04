package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ProductSizeDto;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.model.product.ProductSize;

import java.util.List;

public interface ProductSizeService {

    ProductSize save(ProductSizeDto productSizeDto, Long productDetailId);
    ProductSize findById(Long productSizeId);
    List<ProductSize> findByProductDetailId(Long productDetailId);
    void deleteByProductDetailId(Long productDetailId);
}
