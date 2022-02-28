package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    @Override
    Optional<ProductDetail> findById(Long id);
}
