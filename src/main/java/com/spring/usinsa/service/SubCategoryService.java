package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.SubCategoryDto;
import com.spring.usinsa.model.product.SubCategory;

import java.util.List;

public interface SubCategoryService {

    SubCategory save(SubCategoryDto.Request subCategoryDto);
    SubCategory findById(long id);
    List<SubCategory> findByCategoryId(long categoryId);
    SubCategory updateById(SubCategoryDto.UpdateRequest subCategoryDto);
    void deleteSubCategoryById(long id);
}
