package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Brand;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class BrandDto {

    @Getter
    @Setter
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
    @Builder
    public static class Response{
        private Long brandId;
        private String title;
        private String enTitle;
        private String image;
        private String info;

        public Response(Long brandId, String title, String enTitle, String image, String info){
            this.brandId = brandId;
            this.title = title;
            this.enTitle = enTitle;
            this.image = image;
            this.info = info;
        }

        public static BrandDto.Response toBrandDtoResponse(Brand brand){
            return BrandDto.Response.builder()
                    .brandId(brand.getId())
                    .title(brand.getTitle())
                    .enTitle(brand.getEnTitle())
                    .info(brand.getInfo())
                    .image(brand.getImage()).build();
        }
    }

}
