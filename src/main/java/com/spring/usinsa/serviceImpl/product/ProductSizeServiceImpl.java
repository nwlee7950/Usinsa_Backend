package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductSizeDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.model.product.ProductSize;
import com.spring.usinsa.repository.ProductSizeRepository;
import com.spring.usinsa.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;

    @Override
    public ProductSize save(ProductSizeDto productSizeDto, Long productDetailId) {
        return productSizeRepository.save(productSizeDto.toProductSizeEntity(productDetailId));
    }

    @Override
    public ProductSize findById(Long productSizeId) {
        return productSizeRepository.findById(productSizeId)
                .orElseThrow(()-> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<ProductSize> findByProductDetailId(Long productDetailId) {
        return productSizeRepository.findByProductDetailId(productDetailId);
    }

    @Override
    public void deleteByProductDetailId(Long productDetailId) {

    }
}
