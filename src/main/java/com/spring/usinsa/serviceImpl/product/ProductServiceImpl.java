package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.repository.ProductRepository;
import com.spring.usinsa.service.BrandService;
import com.spring.usinsa.service.ProductService;
import com.spring.usinsa.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.usinsa.serviceImpl.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final SubCategoryService subCategoryService;
    private final MinioService minioService;

    private final String PRODUCT_FOLDER = "product/";
  
    @Override
    public Product save(ProductDto.Request productDto) throws Exception {

        // Product Title Image 추가 과정 이런 식임. SubCategory 도 추가 필요 및
        // ProductDto.Request 에서 toProductEntity(Brand, SubCategory) 같이 변경하고 builder 도 수정 필요
//        Brand brand = brandService.findById(productDto.getBrandId());
//
//        // 새로운 Product 객체 생성 후 Brand 설정
//        Product product = productDto.toProductEntity(brand);
//
//        // 대표 이미지 Upsert
//        String titleImage = minioService.upsertFile(null, PRODUCT_FOLDER, productDto.getTitleImage());
//
//        // TitleImage 설정
//        product.setTitleImage(titleImage);
//
//        return productRepository.save(product);

        Product product = productDto.toProductEntity();
        product.setBrand(brandService.findById(productDto.getBrandId()));
        product.setSubCategory(subCategoryService.findById(productDto.getSubCategoryId()));

        productRepository.save(product);

        return product;
    }

    @Override
    public Product findById(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

//        return toProductDto(product);
        return product;
    }

    @Override
    public Page<Product> findByBrandId(long brandId, Pageable pageable) {
        Page<Product> products = productRepository.findByBrandId(brandId, pageable);

        return products;
    }

    @Override
    public Page<Product> findByBrandIdAndGender(long brandId, String gender, Pageable pageable) {
        Page<Product> products = productRepository.findByBrandIdAndGender(brandId, gender, pageable);

        return products;
    }

    @Override
    public Page<Product> findByBrandIdAndSubCategoryId(long brandId, long subCategoryId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Product> findBySubCategoryId(long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findBySubCategoryId(categoryId, pageable);

        return products;
    }

    @Override
    public Page<Product> findByGender(String gender, Pageable pageable) {
        Page<Product> products = productRepository.findByGender(gender, pageable);

        return products;
    }

    @Override
    public Page<Product> findByPriceBetween(int price1, int price2, long SubCategoryId, Pageable pageable) {
        return productRepository.findByPriceBetweenAndSubCategoryId(price1, price2, SubCategoryId, pageable);
    }

    @Override
    public Page<ProductDto> findByProductRequest(ProductDto.FindProductRequest findProductRequest) {

        //TODO
        //검색 조건 마다 IF문 또는 CASE 작성?

        return null;
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, ProductDto.UpdateRequest productDto) {

        // Product Title Image 수정 과정 이런 식임. SubCategory 도 추가 필요 및
        // ProductDto.Request 에서 toProductEntity(Brand, SubCategory) 같이 변경하고 builder 도 수정 필요
//
//
//        Product product = productService.findById(productDto.getId());
//
//        if(!(productRepository.existsById(productDto.getId())
//                && productId.equals(productDto.getId()))) {
//            throw new ApiException(ApiErrorCode.INVALID_PARAMS);
//        }

//        // 대표 이미지 설정
//        String titleImage = minioService.upsertFile(product.getTitleImage(), PRODUCT_FOLDER, productDto.getTitleImage());
//        product.setTitleImage(titleImage);
//
//        // 브랜드 설정
//        Brand brand = brandService.findById(productDto.getBrandId());
//        product.setBrand(brand);
//
//        return productRepository.save(product);

        Product product = findById(productDto.getId());

        product.setPrice(productDto.getPrice());
        product.setDiscountStartDate(productDto.getDiscountStartDate());
        product.setDiscountEndDate(productDto.getDiscountEndDate());

        //TODO
        // size, Image 수정

        return product;
    }

    @Override
    public void deleteById(long productId) {
        productRepository.deleteById(productId);
    }

}
