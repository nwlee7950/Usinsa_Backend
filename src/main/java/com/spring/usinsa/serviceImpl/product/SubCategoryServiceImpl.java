package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.SubCategoryDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.SubCategory;
import com.spring.usinsa.repository.SubCategoryRepository;
import com.spring.usinsa.service.CategoryService;
import com.spring.usinsa.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    final SubCategoryRepository subCategoryRepository;
    final CategoryService categoryService;

    @Override
    public SubCategory save(SubCategoryDto.Request subCategoryDto) {

        return subCategoryRepository.save(SubCategory.builder()
                .title(subCategoryDto.getTitle())
                .category(categoryService.findById(subCategoryDto.getCategoryId()))
                .build());
    }

    @Override
    public SubCategory findById(long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));
        return subCategory;
    }

    @Override
    public List<SubCategory> findByCategoryId(long categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }

    @Override
    public SubCategory updateById(SubCategoryDto.UpdateRequest subCategoryDto) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryDto.getId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));
        subCategory.setTitle(subCategoryDto.getTitle());

        return subCategory;
    }

    @Override
    public void deleteSubCategoryById(long id) {
        subCategoryRepository.deleteById(id);
    }
}
