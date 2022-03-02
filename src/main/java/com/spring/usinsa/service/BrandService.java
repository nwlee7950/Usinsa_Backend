package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.model.product.Brand;

import java.util.List;

public interface BrandService {
    Brand save(BrandDto.Request brandDto) throws Exception;
    Brand findById(long id);
    List<BrandDto.Response> findAllByTitleContains(String title);
    Brand updateBrand(BrandDto.UpdateRequest brandDto) throws Exception;
    List<BrandDto.ResponseBySubCategory> getBrandListBySubCategory(long subCategory);

}
