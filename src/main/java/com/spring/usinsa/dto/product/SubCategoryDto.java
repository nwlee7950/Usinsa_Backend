package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SubCategoryDto {

    @Getter
    @Setter
    public static class Request{
        private Long categoryId;    // 큰 분류 categoryId
        private String title;

        public SubCategory toSubCategoryEntity(Category category){
            return SubCategory.builder()
                    .category(category)
                    .title(this.title)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private Long id;
        private String title;
        private String categoryTitle;

        public static SubCategoryDto.Response toSubCategoryResponse(SubCategory subCategory){
            return SubCategoryDto.Response.builder()
                    .id(subCategory.getId())
                    .title(subCategory.getTitle())
                    .categoryTitle(subCategory.getCategory().getTitle())
                    .build();
        }
    }

}
