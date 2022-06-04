package com.spring.usinsa.dto.product;

import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDto {

    @Getter
    @Setter
    public static class Request{
        private String title;
        private List<String> subCategoryList;

        public Boolean existsSubCategoryList(){
            if(subCategoryList == null) return false;
            else return true;
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest{
        private String title;
        private List<String> subCategoryList;
        private List<String> addSubCategoryList;

        public Boolean existsSubCategoryList(){
            if(subCategoryList == null) return false;
            else return true;
        }

        public Boolean existsAddSubCategoryList(){
            if(addSubCategoryList == null) return false;
            else return true;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private String title;
        private Long categoryId;
        private List<SubCategoryDto.Response> subCategoryList;

        public static List<SubCategoryDto.Response> toSubCategoryDtoList(List<SubCategory> subCategorys){
            return subCategorys.stream()
                    .map( m -> SubCategoryDto.Response.builder()
                                    .id(m.getId())
                                    .title(m.getTitle()).build())
                    .collect(Collectors.toList());
        }

        public static CategoryDto.Response toCategoryDtoResponse(Category category){
            return CategoryDto.Response.builder()
                    .categoryId(category.getId())
                    .title(category.getTitle())
                    .subCategoryList(CategoryDto.Response.toSubCategoryDtoList(category.getSubCategoryList()))
                    .build();
        }
    }
}
