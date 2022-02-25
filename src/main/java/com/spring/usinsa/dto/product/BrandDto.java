package com.spring.usinsa.dto.product;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class BrandDto {

    @Getter
    public static class CreateRequest {
        private String title;
        private String enTitle;
        private String info;
        private MultipartFile image;
    }

    @Getter
    public static class UpdateRequest{
        private long id;
        private String title;
        private String enTitle;
        private String info;
        private MultipartFile images;

    }

    @Setter
    @Builder
    public static class Response{
        private long brandId;
        private long productTotal;
        private String title;
        private String enTitle;
        private String image;
    }

    @Setter
    public static class ResponseBySubCategory{
        private long brandId;
        private long productTotal;
        private String brandTitle;
        private String subCategory;
//        private Boolean brandLike;
//        private Boolean discount;

        public ResponseBySubCategory(long brandId, long productTotal, String brandTitle, String subCategory){
            this.brandId = brandId;
            this.productTotal = productTotal;
            this.brandTitle = brandTitle;
            this.subCategory = subCategory;
        }
    }

}
