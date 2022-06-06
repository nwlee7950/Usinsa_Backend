package com.spring.usinsa.repository.customRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.spring.usinsa.model.product.QViewedProduct.viewedProduct;

@Repository
@RequiredArgsConstructor
public class ViewedProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 해결하지 못한 부분 productId를 비교하기 위해선 product 데이터를 따로 또 불어와야하는 점
    /**
     * userId, productId로 존재 유무 확인
     * querydsl 사용이유
     * 1. 메소드 이름으로 표현하기 힘듦, productId를 검사하기 위해 product 객체를 불러와야함
     * 2. jpql(@query)은 select exist를 지원하지 않음
     * 3. count는 존재 유무를 넘어 모든 data row를 탐색하기 때문에 성능에 좋지 않음
     * 4. querydsl의 exists는 내부적으로 count를 이용하므로 직접 exists 구현
     @return boolean type exist
     */
    public Boolean existsByUserIdAndProductId(Long userId, Long productId){
        Integer fetchOne = queryFactory
                .selectOne()
                .from(viewedProduct)
                .where(
                        viewedProduct.userId.eq(userId),
                        viewedProduct.product.id.eq(productId)
                )
                .fetchFirst();   // fetchFirst => 내부적으로 limit(1).fetchOne();

        return fetchOne != null;
    }
}
