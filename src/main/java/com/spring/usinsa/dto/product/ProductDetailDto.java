package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.ProductSize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDetailDto {

    @Getter
    public static class UpdateRequest{
        private Long productId;
        private List<ProductSize> productSizeList;
        private ProductImageDto.Request productImageList;
        private MultipartFile contentImage;
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
