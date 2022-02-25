package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.ProductLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    public Boolean existsByUserIdAndProductId(long userId, long productId);
    public Page<ProductLike> findByUserId(long userId, Pageable pageable);
    public long countByProductId(long productId);
    public long deleteByUserIdAndProductId(long userId, long productId);
}
