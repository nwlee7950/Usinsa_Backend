package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    void deleteByProductDetailId(Long productDetailId);
}
