package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.CategoryDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.model.product.SubCategory;
import com.spring.usinsa.repository.CategoryRepository;
import com.spring.usinsa.service.CategoryService;
import com.spring.usinsa.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryService subCategoryService;

    @Override
    @Transactional
    public CategoryDto.Response save(CategoryDto.Request categoryDto) {
        Category category = Category.builder().title(categoryDto.getTitle()).build();

        for (String subCategoryTitle : categoryDto.getSubCategoryList()) {
                category.addSubCategory(new SubCategory(subCategoryTitle));
        }

        Category savedCategory = categoryRepository.save(category);

        return CategoryDto.Response.toCategoryDtoResponse(savedCategory);
    }

    @Override
    public CategoryDto.Response findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        return CategoryDto.Response.toCategoryDtoResponse(category);
    }

    @Override
    public List<CategoryDto.Response> findAll() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(CategoryDto.Response::toCategoryDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDto.Response> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(CategoryDto.Response::toCategoryDtoResponse);
    }

    @Override
    public CategoryDto.Response findByTitle(String title) {
        Category category = categoryRepository.findByTitle(title)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        return CategoryDto.Response.toCategoryDtoResponse(category);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return categoryRepository.existsByTitle(title);
    }

    @Override
    @Transactional
    public CategoryDto.Response update(CategoryDto.UpdateRequest categoryDto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        category.setTitle(categoryDto.getTitle());

        // 삭제될 대상을 찾기 위해 기존 subcategory 담아둠
        Map<Long, String> SubCategoryList = new HashMap<>();
        for(SubCategory subCategory : category.getSubCategoryList()){
            SubCategoryList.put(subCategory.getId(), subCategory.getTitle());
        }

        // 기존 subcategory 업데이트
        for(String subcategory : categoryDto.getSubCategoryList()){
            System.out.println(subcategory);
            String[] subCateArr = subcategory.split(",,");   // 0 = id, 1 = title

            subCategoryService.updateById(Long.parseLong(subCateArr[0]), subCateArr[1]);
            SubCategoryList.remove(Long.parseLong(subCateArr[0]));
        }

        for(Long subCategoryId : SubCategoryList.keySet()){
            subCategoryService.deleteById(subCategoryId);
        }

        if(categoryDto.existsAddSubCategoryList()) {
            for (String subCategoryTitle : categoryDto.getAddSubCategoryList()) {
                if (subCategoryService.existsByTitle(subCategoryTitle)) {
                    throw new ApiException(ApiErrorCode.CATEGORY_TITLE_DUPLICATED);
                }
                category.addSubCategory(new SubCategory(subCategoryTitle));
            }
        }

        return  CategoryDto.Response.toCategoryDtoResponse(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
