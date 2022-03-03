package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SubCategoryDto {

    @Getter
    public static class Request{
        private long categoryId;    // 큰 분류 categoryId
        private String title;

        public SubCategory toSubCategoryEntity(Category category){
            return SubCategory.builder()
                    .category(category)
                    .title(this.title)
                    .build();
        }
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
