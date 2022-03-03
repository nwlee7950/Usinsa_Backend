package com.spring.usinsa.serviceImpl.product;

import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.Brand;
import com.spring.usinsa.model.product.Product;
import com.spring.usinsa.model.product.SubCategory;
import com.spring.usinsa.repository.ProductRepository;
import com.spring.usinsa.service.BrandService;
import com.spring.usinsa.service.ProductService;
import com.spring.usinsa.service.SubCategoryService;
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
    private final BrandService brandService;
    private final SubCategoryService subCategoryService;
    private final MinioService minioService;

    private final String PRODUCT_FOLDER = "product/";
  
    @Override
    public Product save(ProductDto.Request productDto) throws Exception {
        String uploadedTitleImage = minioService.upsertFile(null, PRODUCT_FOLDER, productDto.getTitleImage());
        Brand brand = brandService.findById(productDto.getBrandId());
        SubCategory subCategory = subCategoryService.findById(productDto.getSubCategoryId());

        Product product = productDto.toProductEntity(uploadedTitleImage, brand, subCategory);

        return productRepository.save(product);
    }

    @Override
    public Product findById(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.PRODUCT_NOT_FOUND));

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
    public Product updateProduct(Long productId, ProductDto.UpdateRequest productDto) throws Exception {
        Product product = findById(productDto.getId());

        String titleImage = minioService.upsertFile(null, PRODUCT_FOLDER, productDto.getTitleImage());
        minioService.removeFile(PRODUCT_FOLDER+product.getImage());

        product.setImage(titleImage);

        product.setPrice(productDto.getPrice());
        product.setDiscountStartDate(productDto.getDiscountStartDate());
        product.setDiscountEndDate(productDto.getDiscountEndDate());
        product.setDiscountRate(productDto.getDiscountRate());
        product.setGender(productDto.getGender());
        product.setSubCategory(subCategoryService.findById(productDto.getSubCategoryId()));

        return product;
    }

    @Override
    public void deleteById(long productId) {
        productRepository.deleteById(productId);
    }

}
