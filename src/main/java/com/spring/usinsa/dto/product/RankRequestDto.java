package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Ranking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RankRequestDto {

    private ProductDto.SimpleResponse product;
    private Long count;
    private Long ranking;

    public static RankRequestDto toRankRequestDto(Ranking rank){

        return RankRequestDto.builder()
                .count(rank.getCount())
                .ranking(rank.getRanking())
                .product(ProductDto.SimpleResponse.toProductDtoSimpleResponse(rank.getProduct()))
                .build();
    }
}
