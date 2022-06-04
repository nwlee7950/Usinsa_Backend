package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductDetailDto;
import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.SubCategory;
import com.spring.usinsa.repository.BrandRepository;
import com.spring.usinsa.repository.ProductRepository;
import com.spring.usinsa.repository.SubCategoryRepository;
import com.spring.usinsa.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.usinsa.serviceImpl.MinioService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductDetailService productDetailService;
    private final SubCategoryRepository subCategoryRepository;
    private final MinioService minioService;
    private final ProductLikeService productLikeService;

    private final String PRODUCT_FOLDER = "product/";
  
    @Override
    public Product save(ProductDto.Request productDto) throws Exception{
        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        SubCategory subCategory = subCategoryRepository.findById(productDto.getSubCategoryId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND));

        String uploadedTitleImage = minioService.upsertFile(null, PRODUCT_FOLDER, productDto.getTitleImage());
        Product savedProduct = productRepository.save(productDto.toProductEntity(uploadedTitleImage, brand, subCategory));

        ProductDetailDto.Request productDetail = ProductDetailDto.Request.builder()
                .product(savedProduct)
                .contentImage(productDto.getContentImage())
                .productImageList(productDto.getSubImageList())
                .productSizeList(productDto.getProductSizeList())
                .build();

        productDetailService.save(productDetail);

        return savedProduct;
    }

    @Override
    public ProductDto.Response findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        return ProductDto.Response.toProductDtoResponse(product);
    }

    @Override
    public Product findByIdAsEntity(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public Page<ProductDto.Response> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDto.Response::toProductDtoResponse);
    }

    public Page<ProductDto.SimpleResponse> findAllAsSimpleDto(Pageable pageable){
        return productRepository.findAll(pageable)
                .map(ProductDto.SimpleResponse::toProductDtoSimpleResponse);
    }

    public Page<ProductDto.Response> findByProductRequest(Long categoryId, Long brandId, Pageable pageable) {

        if(brandId != null && brandId > 0){
            if(categoryId != null && categoryId > 0){
                return findByBrandIdAndSubCategoryId(brandId, categoryId, pageable);
            }else{
                return findByBrandId(brandId, pageable);
            }
        }else{
            if(categoryId != null && categoryId > 0){
                return findBySubCategoryId(categoryId, pageable);
            }else{
                return findAll(pageable);
            }
        }
    }

    @Override
    @Transactional
    public ProductDto.Response update(ProductDto.UpdateRequest productDto, Long id) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

        String titleImage = minioService.upsertFile(null, PRODUCT_FOLDER, productDto.getTitleImage());
        minioService.removeFile(PRODUCT_FOLDER+product.getImage());

        product.setImage(titleImage);

        product.setPrice(productDto.getPrice());
        product.setDiscountStartDate(productDto.getDiscountStartDate());
        product.setDiscountEndDate(productDto.getDiscountEndDate());
        product.setDiscountRate(productDto.getDiscountRate());
        product.setGender(productDto.getGender());
        product.setSubCategory(subCategoryRepository.findById(productDto.getSubCategoryId())
                .orElseThrow(() -> new ApiException(ApiErrorCode.CATEGORY_NOT_FOUND)));

        return ProductDto.Response.toProductDtoResponse(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productDetailService.deleteByProductId(id);

        productRepository.deleteById(id);
    }

    public Page<ProductDto.Response> findByBrandId(Long brandId, Pageable pageable) {

        return productRepository.findByBrandId(brandId, pageable)
                .map(m -> ProductDto.Response.toProductDtoResponse(m, productLikeService.countByProductId(m.getId())));
    }

    public Page<ProductDto.Response> findByBrandIdAndSubCategoryId(Long brandId, Long subCategoryId, Pageable pageable) {

        return productRepository.findByBrandIdAndSubCategoryId(brandId, subCategoryId, pageable)
                .map(m -> ProductDto.Response.toProductDtoResponse(m, productLikeService.countByProductId(m.getId())));
    }

    public Page<ProductDto.Response> findBySubCategoryId(Long categoryId, Pageable pageable) {

        return productRepository.findBySubCategoryId(categoryId, pageable)
                .map(m -> ProductDto.Response.toProductDtoResponse(m, productLikeService.countByProductId(m.getId())));
    }
}
