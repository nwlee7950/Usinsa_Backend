package com.spring.usinsa.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {
    void save(MultipartFile image, Long productDetailId);
    void deleteByProductDetailId(Long productDetailId);
}
