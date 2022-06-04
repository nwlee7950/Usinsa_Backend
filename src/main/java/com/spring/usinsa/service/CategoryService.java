package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.CategoryDto;
import com.spring.usinsa.model.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto.Response save(CategoryDto.Request categoryDto);
    CategoryDto.Response findById(Long id);
    List<CategoryDto.Response> findAll();
    Page<CategoryDto.Response> findAll(Pageable pageable);
    CategoryDto.Response findByTitle(String title);
    Boolean existsByTitle(String title);
    CategoryDto.Response update(CategoryDto.UpdateRequest categoryDto, Long id);
    void deleteById(Long id);
}
