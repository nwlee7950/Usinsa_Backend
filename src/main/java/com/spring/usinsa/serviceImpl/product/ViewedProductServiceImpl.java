package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ViewedProductDto;
import com.spring.usinsa.model.product.ViewedProduct;
import com.spring.usinsa.repository.ViewedProductRepository;
import com.spring.usinsa.repository.customRepository.ViewedProductRepositoryCustom;
import com.spring.usinsa.service.ProductService;
import com.spring.usinsa.service.ViewedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ViewedProductServiceImpl implements ViewedProductService {

    private final ViewedProductRepository viewedProductRepository;
    private final ViewedProductRepositoryCustom viewedProductRepositoryCustom;
    private final ProductService productService;

    /**
     * userId와 최근 2일동안 본 상품을 최근 순서로 정렬
     * @param userId
     * @return list<viewedDto>
     */
    @Override
    public List<ViewedProductDto> findByUserId(Long userId) {
        long date = System.currentTimeMillis() - 172800000;
        return viewedProductRepository.findByUserIdAndUpdatedAtGreaterThanOrderByUpdatedAtDesc(userId, date)
                .stream().map(ViewedProductDto::toViewedProductDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ViewedProduct save(Long userId, Long productId) {

        if(viewedProductRepositoryCustom.existsByUserIdAndProductId(userId, productId)){
            ViewedProduct viewedProduct = viewedProductRepository.findByUserIdAndProductId(userId, productId);
            viewedProduct.setUpdatedAt(System.currentTimeMillis());

            return viewedProduct;
        }else{
            return viewedProductRepository.save(
                    ViewedProduct.builder()
                    .userId(userId)
                    .product(productService.findByIdAsEntity(productId))
                    .createdAt(System.currentTimeMillis())
                    .updatedAt(System.currentTimeMillis())
                    .build());
        }
    }

}
