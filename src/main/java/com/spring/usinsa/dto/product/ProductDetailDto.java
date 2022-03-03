package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.ProductDetail;
import com.spring.usinsa.model.product.ProductSize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDetailDto {

    @Getter
    public static class Request{
        private Long productId;
        private List<ProductSize> productSizeList;
        private ProductImageDto.Request productImageList;
        private MultipartFile contentImage;

        public ProductDetail toProductDetailEntity(Product product, String uploadedImage){
            return ProductDetail.builder()
                    .product(product)
                    .contentImage(uploadedImage)
                    .build();
        }
    }

    @Builder
    @Setter
    public static class Response{
        private Long productId;
        private List<ProductSize> productSizeList;
        private List<String> productImageList;
        private ProductDto.FindProductResponse product;
    }
}
