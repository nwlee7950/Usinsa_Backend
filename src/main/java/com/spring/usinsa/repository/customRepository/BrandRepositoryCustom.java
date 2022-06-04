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
