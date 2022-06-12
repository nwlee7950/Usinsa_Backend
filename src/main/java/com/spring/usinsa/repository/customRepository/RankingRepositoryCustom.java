package com.spring.usinsa.repository.customRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.spring.usinsa.model.product.QRanking.ranking1;

@Repository
@RequiredArgsConstructor
public class RankingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public void bulkDeleteByTermType(String termType){

        queryFactory.delete(ranking1)       //ranking table안에 ranking이란 column이 존재해서 ranking1로 만들어진 것으로 보임
                .where(ranking1.termType.eq(termType))
                .execute();
    }

}
