package com.spring.usinsa.repository.customRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.spring.usinsa.model.product.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Boolean existsByUserIdAndProductId(Long userId, Long productId){
        Integer fetchOne = queryFactory
                .selectOne()
                .from(review)
                .where(
                        review.userId.eq(userId),
                        review.productId.eq(productId)
                )
                .fetchFirst();   // fetchFirst => 내부적으로 limit(1).fetchOne();

        return fetchOne != null;
    }
}
