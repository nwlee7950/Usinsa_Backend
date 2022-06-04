package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.BrandBySubCategoryDto;
import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.repository.BrandRepository;
import com.spring.usinsa.repository.customRepository.BrandRepositoryCustom;
import com.spring.usinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.spring.usinsa.serviceImpl.MinioService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandRepositoryCustom brandRepositoryCustom;
    private final MinioService minioService;

    private final String BRAND_FOLDER ="brand/";
    @Override
    public BrandDto.Response save(BrandDto.Request brandDto){

        try {
            String uploadedImage = minioService.upsertFile(null, BRAND_FOLDER, brandDto.getImage());
            Brand savedBrand = brandRepository.save(brandDto.toBrandEntity(uploadedImage));

            return BrandDto.Response.toBrandDtoResponse(savedBrand);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiException(ApiErrorCode.MINIO_INVALID_UPLOAD_REQUEST);
        }
    }

    @Override
    public BrandDto.Response findById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        return BrandDto.Response.toBrandDtoResponse(brand);
    }

    @Override
    public List<BrandDto.Response> findAll() {
        List<Brand> brandList = brandRepository.findAll();

        return brandList.stream().map(BrandDto.Response::toBrandDtoResponse)
                        .collect(Collectors.toList());
    }

    @Override
    public Page<BrandDto.Response> findAll(Pageable pageable) {
        Page<Brand> brandList = brandRepository.findAll(pageable);

        return brandList.map(BrandDto.Response::toBrandDtoResponse);
    }

    @Override
    public List<BrandDto.Response> findByTitleContains(String title) {
        List<Brand> brandList = brandRepository.findByTitleContainsOrEnTitleContains(title, title);
//        List<BrandDto.Response> brandDtoList =brandList.stream().map(m -> BrandDto.Response.toBrandDtoResponse(m))
//                .collect(Collectors.toList());

        return brandList.stream().map(BrandDto.Response::toBrandDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BrandDto.Response update(BrandDto.Request brandDto, Long id){
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        try{
            String image = minioService.upsertFile(null, BRAND_FOLDER, brandDto.getImage());
            minioService.removeFile(brand.getImage());
            brand.setImage(image);
            brand.setTitle(brandDto.getTitle());
            brand.setEnTitle(brandDto.getEnTitle());
            brand.setInfo(brandDto.getInfo());
        }
        catch (Exception e){
            throw new ApiException(ApiErrorCode.MINIO_INVALID_UPLOAD_REQUEST);
        }

        return BrandDto.Response.toBrandDtoResponse(brand);
    }

    @Override
    public List<BrandBySubCategoryDto> findBySubCategory(Long subCategoryId) {
        return brandRepositoryCustom.findBrandListBySubCategory(subCategoryId);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }
}
