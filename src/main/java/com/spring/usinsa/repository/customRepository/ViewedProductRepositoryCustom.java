package com.spring.usinsa.repository.customRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.usinsa.dto.product.RankDto;
import com.spring.usinsa.dto.product.RankRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spring.usinsa.model.product.QProduct.product;
import static com.spring.usinsa.model.product.QViewedProduct.viewedProduct;

@Repository
@RequiredArgsConstructor
public class ViewedProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * userId, productId로 존재 유무 확인
     * querydsl 사용이유
     * 1. 메소드 이름으로 표현하기 힘듦
     * 2. jpql(@query)은 select exist를 지원하지 않음
     * 3. count는 존재 유무를 넘어 모든 data row를 탐색하기 때문에 성능에 좋지 않음
     * 4. querydsl의 exists는 내부적으로 count를 이용하므로 사용하지 않음
     * 5. productId를 검사하기 위해 product 객체를 불러오게 된다. 이때도 n + 1 문제는 발생하기 때문에 join 사용
     @return boolean type exist
     */
    public Boolean existsByUserIdAndProductId(Long userId, Long productId){
        Integer fetchOne = queryFactory
                .selectOne()
                .from(viewedProduct)
                .join(viewedProduct.product, product)
                .where(
                        viewedProduct.product.id.eq(product.id),
                        viewedProduct.userId.eq(userId),
                        viewedProduct.product.id.eq(productId)
                )
                .fetchFirst();   // fetchFirst => 내부적으로 limit(1).fetchOne();

        return fetchOne != null;
    }

    /**
     * date보다 큰 updatedAt을 가진 데이터를 대상으로 group by 실행
     *
     * querydsl 사용이유
     * 1. 메소드 이름으로 표현하기 힘듦
     * 2. query 최적화
     @return List<RankDto> , limit 100
     */
    public List<RankDto> getRankByGreaterThanUpdatedAt( long date ){
        List<RankDto> result = queryFactory
                .select(
                        Projections.constructor(RankDto.class,
                                viewedProduct.product.id,
                                viewedProduct.product.count()
                                )
                )
                .from(viewedProduct)
                .join(viewedProduct.product, product)
                .where(
                        viewedProduct.product.id.eq(product.id),
                        viewedProduct.updatedAt.gt(date)
                )
                .groupBy(viewedProduct.product.id)
                .having()
                .orderBy(viewedProduct.product.count().asc())
                .limit(100)
                .fetch();

        return result;
    }
}
