package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.model.product.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    BrandDto.Response save(BrandDto.Request brandDto);
    BrandDto.Response findById(Long id);
    List<BrandDto.Response> findAll();
    Page<BrandDto.Response> findAll(Pageable pageable);
    List<BrandDto.Response> findByTitleContains(String title);
    BrandDto.Response update(BrandDto.Request brandDto, Long id);
    List<BrandBySubCategoryDto> findBySubCategory(Long subCategoryId);
    void deleteById(Long id);

}
