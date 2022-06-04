package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Cart;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.ProductSize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDto {
    private Long productId;
    private int total;
    private Long productSizeId;
    private Long userId;

    public Cart toCartEntity(Product product, ProductSize productSize){
        return Cart.builder()
                .total(total)
                .userId(userId)
                .productSize(productSize)
                .product(product)
                .build();
    }
}
