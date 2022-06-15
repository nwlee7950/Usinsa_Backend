package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.Ranking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankDto {

    private Long productId;
    private Long count;
    private Long ranking;
    private Product product;

    public RankDto(Long productId, Long count){
        this.productId =productId;
        this.count = count;
    }

    public Ranking toRankingEntity(String termType){
        return Ranking.builder()
                .ranking(ranking)
                .count(count)
                .product(product)
                .termType(termType)
                .build();
    }
}
