package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.ViewedProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ViewedProductDto {
    private Long productId;
    private String brandTitle;
    private String productTitle;
    private Long createdAt;
    private Long updatedAt;
    private int price;
    private String image;

    public static ViewedProductDto toViewedProductDto(ViewedProduct viewedProduct){
        return ViewedProductDto.builder()
                .productId(viewedProduct.getProduct().getId())
                .productTitle(viewedProduct.getProduct().getTitle())
                .image(viewedProduct.getProduct().getImage())
                .brandTitle(viewedProduct.getProduct().getBrand().getTitle())
                .price(viewedProduct.getProduct().getPrice())
                .createdAt(viewedProduct.getCreatedAt())
                .updatedAt(viewedProduct.getUpdatedAt())
                .build();
    }
}
