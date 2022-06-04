package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product save(ProductDto.Request productDto) throws Exception;
    ProductDto.Response findById(Long productId);
    Product findByIdAsEntity(Long productId);
    Page<ProductDto.SimpleResponse> findAllAsSimpleDto(Pageable pageable);
    Page<ProductDto.Response> findAll(Pageable pageable);
    Page<ProductDto.Response> findByProductRequest(Long categoryId, Long brandId, Pageable pageable);
    ProductDto.Response update(ProductDto.UpdateRequest productDto, Long id) throws Exception;

    void deleteById(Long productId);
}
