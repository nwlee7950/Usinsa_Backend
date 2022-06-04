package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.SubCategoryByBrandIdDto;
import com.spring.usinsa.dto.product.SubCategoryDto;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.SubCategory;

import java.util.List;

public interface SubCategoryService {
    SubCategory save(Category category, String title);
    SubCategoryDto.Response findById(Long id);
    List<SubCategoryDto.Response> findAll();
    List<SubCategoryDto.Response> findByCategoryId(Long categoryId);
    SubCategoryDto.Response updateById(Long id, String title);
    List<SubCategoryByBrandIdDto> findByBrandId(Long brandId);
    Boolean existsByTitle(String title);
    void deleteById(Long id);
}
