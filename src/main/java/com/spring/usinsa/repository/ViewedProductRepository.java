package com.spring.usinsa.repository;

import com.spring.usinsa.model.product.ViewedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewedProductRepository extends JpaRepository<ViewedProduct, Long> {
    List<ViewedProduct> findByUserIdAndUpdatedAtGreaterThanOrderByUpdatedAtDesc(Long userId, long date);

    @Query("select v " +
            " from ViewedProduct as v " +
            " where v.userId = :user_id and v.product.id = :product_id ")
    ViewedProduct findByUserIdAndProductId(@Param("user_id") Long userId, @Param("product_id") Long productId );


}
