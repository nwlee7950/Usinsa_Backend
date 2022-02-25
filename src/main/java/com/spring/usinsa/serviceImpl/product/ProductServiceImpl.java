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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    BrandService brandService;
    SubCategoryService subCategoryService;

    @Override
    public Product save(ProductDto.Request productDto) {
        Product product = productDto.toProductEntity();
        product.setBrand(brandService.findById(productDto.getBrandId()));
        product.setSubCategory(subCategoryService.findById(productDto.getSubCategoryId()));

        productRepository.save(product);

        //TODO
        // product 저장 후 productId와 dto에서 정보를 꺼내 image, size, discount 등을 저장
        // 각각의 service 작성 후 이어할 계획
//        ProductImage productImage
//        productImageService.save(productImage);
//        ProductSize productsize
//        productSizeService.save(productImage);
//        ProductDiscount productDiscount
//        productDiscountService.save(productDiscount);

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
    public Product updateProduct(ProductDto.UpdateRequest productDto) {
        Product product = findById(productDto.getId());

        product.setDeliveryInfo(productDto.getDeliveryInfo());
        product.setPrice(productDto.getPrice());
        product.setDiscountStartDate(productDto.getDiscountStartDate());
        product.setDiscountEndDate(productDto.getDiscountEndDate());

        //TODO
        // size, discount, delivery, Image 수정은
        // 각각 서비스 준비되면 다시 작성 예정

        return product;
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

}
