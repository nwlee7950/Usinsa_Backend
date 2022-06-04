package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.CategoryDto;
import com.spring.usinsa.dto.product.ProductImageDto;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    void save(ProductImageDto.Request productImageDto);
    void deleteByProductDetailId(Long productDetailId);
}
