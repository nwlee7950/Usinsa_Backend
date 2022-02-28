package com.spring.usinsa.service;

import com.spring.usinsa.model.product.ProductDetail;

public interface ProductDetailService {
    public ProductDetail save(ProductDetail productDetail);
    public ProductDetail findById(long id);
}
