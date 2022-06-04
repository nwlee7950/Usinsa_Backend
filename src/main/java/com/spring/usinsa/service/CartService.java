package com.spring.usinsa.service;


import com.spring.usinsa.dto.product.CartDto;
import com.spring.usinsa.dto.product.CartResponseDto;

import java.util.List;

public interface CartService {
    CartResponseDto save(CartDto cartDto);
    List<CartResponseDto> findByUserId(Long userId);
    int countByUserId(Long userId);
    void deleteById(Long cartId);
}
