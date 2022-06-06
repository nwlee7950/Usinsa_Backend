package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Override
    Optional<Cart> findById(Long cartId);
    List<Cart> findByUserId(Long userId);

    @Query(" select count(c) from Cart as c where c.userId = :userId and c.productSize.id = :productSizeId ")
    Integer existsByUserIdAndProductSizeId(@Param("userId") Long userId, @Param("productSizeId") Long productSizeId);
    int countByUserId(Long userId);

    @Query("select c " +
            " from Cart as c " +
            " where c.userId = :user_id and c.productSize.id = :product_size_id ")
    Cart findByUserIdAndProductSizeId(@Param("user_id") Long userId, @Param("product_size_id") Long productSizeId );
}
