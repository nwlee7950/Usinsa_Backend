package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.repository.BrandRepository;
import com.spring.usinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.usinsa.serviceImpl.MinioService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {


    private final BrandRepository brandRepository;
    private final MinioService minioService;

    private final String BRAND_FOLDER ="brand/";
    @Override
    public Brand save(BrandDto.Request brandDto) throws Exception {

        Brand brand = brandDto.toBrandEntity();
        String image = minioService.upsertFile(null, BRAND_FOLDER, brandDto.getImage());
        brand.setImage(image);

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
    public Brand updateBrand(BrandDto.UpdateRequest brandDto) throws Exception{
        Brand brand = brandRepository.findById(brandDto.getId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        String image = minioService.upsertFile(null, BRAND_FOLDER, brandDto.getImage());
        minioService.removeFile(brand.getImage());

        brand.setImage(image);
        brand.setTitle(brandDto.getTitle());
        brand.setEnTitle(brandDto.getEnTitle());
        brand.setInfo(brandDto.getInfo());

        return brand;
    }

    @Override
    public List<BrandDto.ResponseBySubCategory> getBrandListBySubCategory(long subCategory) {
        return brandRepository.findBrandListBySubCategory(subCategory);
    }
}
