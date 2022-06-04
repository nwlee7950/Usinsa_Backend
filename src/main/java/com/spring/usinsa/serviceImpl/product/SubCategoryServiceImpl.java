package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.SubCategoryByBrandIdDto;
import com.spring.usinsa.dto.product.SubCategoryDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.SubCategory;
import com.spring.usinsa.repository.SubCategoryRepository;
import com.spring.usinsa.service.CategoryService;
import com.spring.usinsa.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SubCategory save(Category category, String title) {
        return subCategoryRepository.save(SubCategory.builder().title(title).category(category).build());
    }

    @Override
    public SubCategoryDto.Response findById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        return SubCategoryDto.Response.toSubCategoryResponse(subCategory);
    }

    @Override
    public List<SubCategoryDto.Response> findAll() {
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();

        return subCategoryList.stream()
                .map(SubCategoryDto.Response::toSubCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubCategoryDto.Response> findByCategoryId(Long categoryId) {
        List<SubCategory> subCategoryList = subCategoryRepository.findByCategoryId(categoryId);

        return subCategoryList.stream()
                .map(SubCategoryDto.Response::toSubCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubCategoryDto.Response updateById(Long id, String title) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));
        subCategory.setTitle(title);

        return SubCategoryDto.Response.toSubCategoryResponse(subCategory);
    }

//    @Override
//    public SubCategoryDto.Response findByTitle(String title) {
//        SubCategory subCategory = subCategoryRepository.findByTitle(title)
//                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));
//
//        return SubCategoryDto.Response.toSubCategoryResponse(subCategory);
//    }

    @Override
    public List<SubCategoryByBrandIdDto> findByBrandId(Long brandId) {
        return subCategoryRepository.findByBrandId(brandId);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return subCategoryRepository.existsByTitle(title);
    }

    @Override
    public void deleteById(Long id) {
        subCategoryRepository.deleteById(id);
    }
}
