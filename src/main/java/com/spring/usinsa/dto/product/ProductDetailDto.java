package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.ProductDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailDto {

    @Getter
    @Setter
    @Builder
    public static class Request{
        private Product product;
        private List<ProductSizeDto> productSizeList;
        private List<ProductImageDto.Request> productImageList;
        private MultipartFile contentImage;

        public ProductDetail toProductDetailEntity(String uploadedImage){
            return ProductDetail.builder()
                    .product(product)
                    .contentImage(uploadedImage)
                    .build();
        }

    }
    @Builder
    @Getter
    @Setter
    public static class Response{
        private Long id;
        private String image;
        private List<ProductSizeDto> productSizeList;
        private List<String> subImageList;

        private ProductDto.Response product;

        public static ProductDetailDto.Response toProductDetailDtoResponse(ProductDetail productDetail){
            return ProductDetailDto.Response.builder()
                    .id(productDetail.getId())
                    .image(productDetail.getContentImage())
                    .productSizeList(productDetail.getProductSize().stream().map(ProductSizeDto::toProductSizeDto).collect(Collectors.toList()))
                    .subImageList(productDetail.getProductImages().stream().map(m -> m.getImage()).collect(Collectors.toList()))
                    .product(ProductDto.Response.toProductDtoResponse(productDetail.getProduct()))
                    .build();
        }
    }
}
