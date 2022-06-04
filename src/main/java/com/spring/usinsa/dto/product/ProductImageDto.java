package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.ProductImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request{
        private MultipartFile image;
        private Long productId;

        public Request(MultipartFile image){
            this.image=image;
        }

        public ProductImage toProductImageEntity(String image){
            return ProductImage.builder()
                    .image(image)
                    .productDetailId(this.productId)
                    .build();
        }
    }
}
