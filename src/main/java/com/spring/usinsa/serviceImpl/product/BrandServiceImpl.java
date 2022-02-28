package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.repository.BrandRepository;
import com.spring.usinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {


    final BrandRepository brandRepository;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand findById(long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        return brand;
    }

    @Override
    public List<BrandDto.Response> findAllByTitleContains(String title) {
//        return brandRepository.getBrandDtoByTitle(title);
        return null;
    }

    @Override
    @Transactional
    public Brand updateBrand(BrandDto.UpdateRequest brandDto) {
        Brand brand = brandRepository.findById(brandDto.getId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        brand.setTitle(brandDto.getTitle());
        brand.setEnTitle(brandDto.getEnTitle());
        brand.setInfo(brandDto.getInfo());

        //TODO
        //기존 이미지 삭제 및 업로드

        return brand;
    }

    @Override
    public List<BrandDto.ResponseBySubCategory> getBrandListBySubCategory(long subCategory) {
        return brandRepository.findBrandListBySubCategory(subCategory);
    }
}
