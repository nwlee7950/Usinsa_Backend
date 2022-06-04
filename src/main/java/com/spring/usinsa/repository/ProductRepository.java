package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);
    Page<Product> findBySubCategoryId(Long subCategoryId, Pageable pageable);
    Page<Product> findByBrandId(Long brandId, Pageable pageable);
    Page<Product> findByBrandIdAndSubCategoryId(Long brandId, Long categoryId, Pageable pageable);
    Page<Product> findByGender(String gender, Pageable pageable);
    Page<Product> findByBrandIdAndGender(Long brandId, String gender , Pageable pageable);
    Page<Product> findByPriceBetweenAndSubCategoryId(int price1, int price2, Long subCategoryId, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
}
