package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.*;
import com.spring.usinsa.service.ProductLikeService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Request{
        private int price;
        private Long discountStartDate;
        private Long discountEndDate;
        private String title;
        private String gender;
        private int discountRate; // 할인율

        private Long subCategoryId;
        private Long brandId;
        private MultipartFile titleImage;   // 대표 이미지
        private MultipartFile contentImage; // 상품 설명 이미지

        private List<ProductSizeDto> productSizeList;
        private List<ProductImageDto.Request> subImageList;

        public Product toProductEntity(String uploadedTitleImage, Brand brand, SubCategory subCategory){
            return Product.builder()
                    .discountStartDate(this.discountStartDate)
                    .discountEndDate(this.discountEndDate)
                    .discountRate(this.discountRate)
                    .gender(this.gender)
                    .price(this.price)
                    .title(this.title)
                    .image(uploadedTitleImage)
                    .brand(brand)
                    .subCategory(subCategory)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest{
        private int price;
        private long discountStartDate;
        private long discountEndDate;
        private String title;
        private String gender;
        private int discountRate;
        private MultipartFile titleImage;

        private long subCategoryId;
    }

    @Setter
    @Getter
    @Builder
    public static class Response{
        private Long id;
        private int price;
        private String title;
        private String gender;
        private String titleImage;

        private String categoryTitle;
        private String subCategoryTitle;

        private BrandDto.Response brand;
        private int likeCount;

        public static ProductDto.Response toProductDtoResponse(Product product){
            return Response.builder()
                    .id(product.getId())
                    .price(product.getPrice())
                    .title(product.getTitle())
                    .gender(product.getGender())
                    .titleImage(product.getImage())
                    .brand(BrandDto.Response.toBrandDtoResponse(product.getBrand()))
                    .subCategoryTitle(product.getSubCategory().getTitle())
                    .categoryTitle(product.getSubCategory().getCategory().getTitle())
                    .build();
        }

        public static ProductDto.Response toProductDtoResponse(Product product, int likeCount){
            return Response.builder()
                    .id(product.getId())
                    .price(product.getPrice())
                    .title(product.getTitle())
                    .gender(product.getGender())
                    .titleImage(product.getImage())
                    .brand(BrandDto.Response.toBrandDtoResponse(product.getBrand()))
                    .subCategoryTitle(product.getSubCategory().getTitle())
                    .categoryTitle(product.getSubCategory().getCategory().getTitle())
                    .likeCount(likeCount)
                    .build();
        }

    }

    @Setter
    @Getter
    @Builder
    public static class SimpleResponse{
        private Long id;
        private String brandTitle;
        private int price;
        private String title;
        private String titleImage;

        public static ProductDto.SimpleResponse toProductDtoSimpleResponse(Product product){
            return SimpleResponse.builder()
                    .id(product.getId())
                    .price(product.getPrice())
                    .title(product.getTitle())
                    .titleImage(product.getImage())
                    .brandTitle(product.getBrand().getTitle())
                    .build();
        }

    }

}
