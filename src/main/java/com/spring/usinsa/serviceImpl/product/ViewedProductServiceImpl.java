package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.RankDto;
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
    @Transactional
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

    @Override
    @Transactional
    public List<RankDto> getDailyLank() {
        long date = System.currentTimeMillis() - 86400000;
        List<RankDto> result = viewedProductRepositoryCustom.getRankByGreaterThanUpdatedAt(date);

        Long idx = 1l;
        for ( RankDto rankDto : result ) {
            rankDto.setRanking(idx++);
            rankDto.setProduct(productService.findByIdAsEntity(rankDto.getProductId()));
        }

        return result;
    }

    // fetch join 후 product를 바로 rankDto에 담아 ranking table에 바로 저장하고 싶지만
    // querydsl에선 dto로 결과 조회 시 fetch join을 지원하지 않아 product 하나하나 검색하게 되었음
    @Override
    public List<RankDto> getEveryTimeLank() {
        long date = System.currentTimeMillis() - 86400000;
        List<RankDto> result = viewedProductRepositoryCustom.getRankByGreaterThanUpdatedAt(date);

        Long idx = 1l;
        for ( RankDto rankDto : result ) {
            rankDto.setRanking(idx++);
            rankDto.setProduct(productService.findByIdAsEntity(rankDto.getProductId()));
        }

        return result;
    }
}
