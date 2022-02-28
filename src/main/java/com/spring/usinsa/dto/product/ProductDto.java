package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.*;
import lombok.*;

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

        private DeliveryInfoDto deliveryInfo;
        private long subCategoryId;
        private long brandId;

        private List<ProductSize> productSizeList;
        private ProductImageDto.Request productImageList;
        private List<ProductDiscountDetail> productDiscountDetailList;

        public Product toProductEntity(){
            return Product.builder()
                    .discountStartDate(this.discountStartDate)
                    .discountEndDate(this.discountEndDate)
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

        private DeliveryInfo deliveryInfo;
        private SubCategory subCategory;
        private Brand brand;

        private List<ProductSize> productSizeList;
        private List<ProductImageDto.Request> productImageList;
        private List<ProductDiscountDetail> productDiscountDetailList;
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

        private DeliveryInfo deliveryInfo;
        private SubCategory subCategory;
        private Brand brand;

        private List<ProductSize> productSizeList;
        private List<ProductImageDto.Response> productImageList;
        private List<ProductDiscountDetail> productDiscountDetailList;
    }


}
