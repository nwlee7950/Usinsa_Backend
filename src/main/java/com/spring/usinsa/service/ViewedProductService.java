package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ViewedProductDto;
import com.spring.usinsa.model.product.ViewedProduct;

import java.util.List;

public interface ViewedProductService {
    List<ViewedProductDto> findByUserId(Long userId);
    ViewedProduct save(Long userId, Long productId);
}
