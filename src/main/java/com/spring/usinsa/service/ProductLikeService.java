package com.spring.usinsa.service;

import com.spring.usinsa.model.product.ProductLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ProductLikeService {

    ProductLike createProductLike(long userId, long productId);
    void deleteProductLikeByUserIdAndProductId(long userId, long productId);

    Boolean existsProductLikeByUserIdAndProductId(long userId, long productId);
    long countProductLikeByProductId(long productId);
    Page<ProductLike> countProductLikeByUserId(long userId, Pageable pageable);
}