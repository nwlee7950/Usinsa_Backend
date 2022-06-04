package com.spring.usinsa.service;

import com.spring.usinsa.model.product.ProductLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ProductLikeService {

    ProductLike save(Long userId, Long productId);
    void deleteProductLikeByUserIdAndProductId(Long userId, Long productId);

    Boolean existsProductLikeByUserIdAndProductId(Long userId, Long productId);
    int countByProductId(Long productId);
    Page<ProductLike> countProductLikeByUserId(Long userId, Pageable pageable);
}