package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Ranking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RankRequestDto {

    private ProductDto.Response product;
    private Long count;
    private Long ranking;

    public static RankRequestDto toRankRequestDto(Ranking rank){

        return RankRequestDto.builder()
                .count(rank.getCount())
                .ranking(rank.getRanking())
                .product(ProductDto.Response.toProductDtoResponse(rank.getProduct()))
                .build();
    }
}
