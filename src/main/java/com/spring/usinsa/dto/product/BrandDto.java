package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Brand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class BrandDto {

    @Getter
    public static class Request {
        private String title;
        private String enTitle;
        private String info;
        private MultipartFile image;

        public Brand toBrandEntity(String uploadedImage){
            return Brand.builder()
                    .title(this.title)
                    .enTitle(this.enTitle)
                    .info(this.info)
                    .image(uploadedImage)
                    .build();
        }
    }

    @Getter
    public static class UpdateRequest{
        private long id;
        private String title;
        private String enTitle;
        private String info;
        private MultipartFile image;

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
