package com.spring.usinsa.dto.product;


import com.spring.usinsa.model.product.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartResponseDto {

    private Long cartId;
    private Long createdAt;
    private int total;
    private String productTitle;
    private String productSize;
    private String productImage;
    private String productBrandTitle;
    private int productPrice;
    private int productTotal;

    public static CartResponseDto toCartResponseDto(Cart cart){
        return CartResponseDto.builder()
                .cartId(cart.getId())
                .createdAt(cart.getCreatedAt())
                .total(cart.getTotal())
                .productImage(cart.getProduct().getImage())
                .productPrice(cart.getProduct().getPrice())
                .productTitle(cart.getProduct().getTitle())
                .productBrandTitle(cart.getProduct().getBrand().getTitle())
                .productSize(cart.getProductSize().getSize())
                .productTotal(cart.getProductSize().getTotal())
                .build();
    }
}
