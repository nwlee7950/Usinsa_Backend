package com.spring.usinsa.service;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.dto.product.ProductDto;
import com.spring.usinsa.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product save(ProductDto.Request productDto);

    Product findById(long productId);
    Page<Product> findByBrandId(long brandId, Pageable pageable);
    Page<Product> findByBrandIdAndGender(long brandId, String gender, Pageable pageable);
    Page<Product> findByBrandIdAndSubCategoryId(long brandId, long subCategoryId, Pageable pageable);
    Page<Product> findBySubCategoryId(long categoryId, Pageable pageable);
    Page<Product> findByGender(String gender, Pageable pageable);
    Page<Product> findByPriceBetween(int price1, int price2, long SubCategoryId, Pageable pageable);

    Page<ProductDto> findByProductRequest(ProductDto.FindProductRequest findProductRequest);

    Product updateProduct(ProductDto.UpdateRequest productDto);
    void deleteProduct(long productId);
}
