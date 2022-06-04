package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.ProductLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    public Boolean existsByUserIdAndProductId(Long userId, Long productId);
    public Page<ProductLike> findByUserId(Long userId, Pageable pageable);
    int countByProductId(Long productId);
    Long deleteByUserIdAndProductId(Long userId, Long productId);
}
