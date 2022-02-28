package com.spring.usinsa.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageDto {

    @Getter
    public static class Request{
        private MultipartFile image;
        private long productId;
    }

    @Setter
    @Builder
    public static class Response{
        private List<String> images;
        private long productId;
    }
}
