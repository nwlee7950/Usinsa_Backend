package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request{
        private int price;
        private long discountStartDate;
        private long discountEndDate;
        private String title;
        private String gender;
        private int discountRate; // 할인율

        private Long subCategoryId;
        private Long brandId;
        private MultipartFile titleImage;   // 대표 이미지

        private List<ProductSize> productSizeList;
        private ProductImageDto.Request productImageList;

        public Product toProductEntity(){
            return Product.builder()
                    .discountStartDate(this.discountStartDate)
                    .discountEndDate(this.discountEndDate)
                    .discountRate(this.discountRate)
                    .gender(this.gender)
                    .price(this.price)
                    .title(this.title)
                    .build();
        }
    }

    @Getter
    public static class UpdateRequest{
        private long id;
        private int price;
        private long discountStartDate;
        private long discountEndDate;
        private String title;
        private String gender;
        private int discountRate;
        private MultipartFile titleImage;

        private long subCategoryId;
    }

    @Getter
    @Setter
    @Builder
    public static class FindProductRequest{
        private long id;
        private int price1;
        private int price2;
        private String gender;
        private long subCategoryId;
        private long brandId;
        private int page;
        private String sort;
    }

    @Setter
    @Builder
    public static class FindProductResponse{
        private long id;
        private int price;
        private long discountStartDate;
        private long discountEndDate;
        private String title;
        private String gender;
        private int discountRate;
        private String titleImage;

        private SubCategory subCategory;
        private Brand brand;
    }


}
