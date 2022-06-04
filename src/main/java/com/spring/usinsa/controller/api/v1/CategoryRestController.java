package com.spring.usinsa.controller.api.v1;

import com.spring.usinsa.dto.product.CategoryDto;
import com.spring.usinsa.dto.product.SubCategoryByBrandIdDto;
import com.spring.usinsa.dto.product.SubCategoryDto;

import com.spring.usinsa.response.SingleResponse;
import com.spring.usinsa.service.CategoryService;
import com.spring.usinsa.service.SubCategoryService;
import com.spring.usinsa.serviceImpl.ApiResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryRestController {

    private final ApiResponseService apiResponseService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @GetMapping
    public SingleResponse<List<CategoryDto.Response>> findAll(){

        List<CategoryDto.Response> categoryList = categoryService.findAll();

        return apiResponseService.getSingleResult(categoryList);
    }

    @GetMapping("/{id}")
    public SingleResponse<CategoryDto.Response> findById(@PathVariable Long id){

        CategoryDto.Response category = categoryService.findById(id);

        return apiResponseService.getSingleResult(category);
    }

    @GetMapping("/subCategory/{id}")
    public SingleResponse<SubCategoryDto.Response> findBySubCategoryId(@PathVariable Long id){

        SubCategoryDto.Response category = subCategoryService.findById(id);

        return apiResponseService.getSingleResult(category);
    }

    @GetMapping("/brand/{id}")
    public SingleResponse<List<SubCategoryByBrandIdDto>> findByBrandId(@PathVariable Long id){

        List<SubCategoryByBrandIdDto> category = subCategoryService.findByBrandId(id);

        return apiResponseService.getSingleResult(category);
    }

}