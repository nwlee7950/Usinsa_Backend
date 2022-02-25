package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Category;
import com.spring.usinsa.repository.CategoryRepository;
import com.spring.usinsa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public Category save(String title) {
        return categoryRepository.save(Category.builder().title(title).build());
    }

    @Override
    public Category findById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
