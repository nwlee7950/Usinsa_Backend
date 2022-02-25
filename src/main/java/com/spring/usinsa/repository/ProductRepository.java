package com.spring.usinsa.repository;

import com.spring.usinsa.dto.product.BrandDto;
import com.spring.usinsa.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(long id);
    Page<Product> findBySubCategoryId(long subCategoryId, Pageable pageable);
    Page<Product> findByBrandId(long brnadId, Pageable pageable);
    Page<Product> findByBrandIdAndSubCategoryId(long brnadId, long categoryId, Pageable pageable);
    Page<Product> findByGender(String gender, Pageable pageable);
    Page<Product> findByBrandIdAndGender(long brandId, String gender , Pageable pageable);
    Page<Product> findByPriceBetweenAndSubCategoryId(int price1, int price2, long subCategoryId, Pageable pageable);

}
