package com.spring.usinsa.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SubCategoryDto {

    @Getter
    public static class Request{
        private long categoryId;    // 큰 categoryId
        private String title;
    }

    @Setter
    @Builder
    public static class Response{
        private long id;            // subCategoryId
        private String subCategoryTitle;
        private long categoryId;    // 큰 categoryId
        private String title;
    }

    @Getter
    public static class UpdateRequest{
        private long id;
        private String title;
    }
}
