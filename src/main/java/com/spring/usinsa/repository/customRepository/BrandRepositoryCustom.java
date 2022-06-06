package com.spring.usinsa.repository.customRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spring.usinsa.model.product.QBrand.brand;
import static com.spring.usinsa.model.product.QProduct.product;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
        * subCategoryId에 해당하는 상품을 파는 모든 브랜드 검색
        * querydsl 사용이유 1. brand entity로 받을 수 없는 검색결과, 2. query성능 최적화
        @param subCategoryId, 서브 카테고리 id
        @return List<brandBySubCategoryDto> , brand와 판매중인 상품 개수
     */
    public List<BrandBySubCategoryDto> findBrandListBySubCategory(Long subCategoryId){

        List<BrandBySubCategoryDto> result = queryFactory
                .select(
                        Projections.constructor(BrandBySubCategoryDto.class,
                        brand.id,
                        brand.title,
                        brand.count())
                )
                .from(brand, product)
                .where(
                        brand.id.eq(product.brand.id),
                        product.subCategory.id.eq(subCategoryId)
                )
                .groupBy(product.brand.id)
                .fetch();

        return result;
    }
}
